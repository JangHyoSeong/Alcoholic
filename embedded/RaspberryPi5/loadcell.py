import serial
import time
import json
from dotenv import load_dotenv
from collections import deque
import os
import requests
import threading

load_dotenv()
server_api_url = os.getenv("SERVER_API_URL")
token = os.getenv("TOKEN")

weight_queue = deque(maxlen=3)
weight_cnt = [0,0,0,0]
ARR_LEN = 4
DETECTION_THRESHOLD = 2
THRESHOLD_WEIGHT = 120
refrigerator_id = 1

# register_event = threading.Event()
register_event = False

def register_drink(refrigerator_id, drink_name, position, image_path):
    url = f"{server_api_url}/refrigerators/admin/{refrigerator_id}"
    headers = {
        "Authorization": token,
        # "Content-Type": "multipart/form-data"
    }

    data = {
        "drinkName": drink_name,
        "position": position
    }

    files = {
        "image": ("image.jpg", open(image_path, "rb"), "image/jpeg")  # 이미지 파일
    }

    try:
        print("POST 요청 전송 중...")
        response = requests.post(url, headers=headers, data=data, files=files)
        response.raise_for_status()  # HTTP 오류가 발생하면 예외 발생

        # JSON 응답 파싱
        result = response.json().get("result")
        print("POST 요청 성공:", response)
        return result

    except requests.exceptions.RequestException as e:
        print("POST 요청 실패:", e)
        return None


def delete_drink(refrigerator_id, position):
    url = f"{server_api_url}/refrigerators/admin/stocks/{refrigerator_id}"
    headers = {
        "Authorization": token,
        "Content-Type": "application/json"
    }
    data = {
        "position": position
    }

    try:
        response = requests.delete(url, headers=headers, json=data)  # data 대신 json 사용
        response.raise_for_status()  # HTTP 오류 발생 시 예외 발생

        # JSON 응답 파싱
        result = response.json().get("result")
        if result == "deleted":
            print(f"DELETE 요청 성공: 위치 {position}의 술이 삭제되었습니다.")
        else:
            print("삭제 실패:", response.json())
        return result

    except requests.exceptions.RequestException as e:
        print("DELETE 요청 실패:", e)
        return None


def get_inventory(refrigerator_id, ARR_LEN):
    headers = {
        "Authorization": token  # GET 요청에는 Content-Type이 필요하지 않음
    }
    try:
        response = requests.get(f"{server_api_url}/refrigerators/{refrigerator_id}", headers=headers)
        response.raise_for_status()  # HTTP 오류 발생 시 예외 발생
        data = response.json()       # JSON 데이터를 딕셔너리로 변환
        results = data.get("results", [])

        visited = [False] * ARR_LEN

        for item in results:
            position = item.get("position")
            if position and 1 <= position <= ARR_LEN:
                visited[position - 1] = True
        print("get 요청 성공: ", visited)
        return visited

    except requests.exceptions.RequestException as e:
        print("요청 중 오류 발생:", e)
        return []

def registration_detection(inventory, idx, product_name):
    global registration_successful

    if len(weight_queue) > 0 and weight_queue[-1][idx] >= THRESHOLD_WEIGHT and weight_cnt[idx] < DETECTION_THRESHOLD:
        weight_cnt[idx] += 1
    else:
        weight_cnt[idx] = 0
    if weight_cnt[idx] == DETECTION_THRESHOLD:
        ## POST 등록
        print("POST 요청을 위해 등록 감지됨:", idx + 1)  # 디버깅 메시지 추가
        result = register_drink(refrigerator_id, product_name, idx+1, "captured_image.jpg")
        
        if result is not None:  # POST 요청 성공 시 등록 완료로 플래그 설정
            print("ok")
            registration_successful = True
            inventory[idx] = True


def removal_detection(inventory, idx):
    if len(weight_queue) > 0 and weight_queue[-1][idx] < THRESHOLD_WEIGHT and weight_cnt[idx] > 0:
        weight_cnt[idx] -= 1
    else:
        weight_cnt[idx] = DETECTION_THRESHOLD
    if weight_cnt[idx] == 0:
        ## delete 제거
        delete_drink(refrigerator_id, idx+1)
        inventory[idx] = False

def start_registration_monitoring(ser, product_name=None):
    """등록 모드에서 로드셀 데이터 모니터링 함수"""
    global registration_successful, register_event
    registration_successful = False

    # 등록 이벤트 활성화 (삭제 모드가 대기하도록 설정)
    register_event = True

    inventory = get_inventory(refrigerator_id, ARR_LEN)
    time.sleep(2)  # 시리얼 초기화 대기

    try:
        while True:
            if ser.in_waiting > 0:
                data = list(map(int, ser.readline().decode('utf-8').strip().split()))

                if len(data) == ARR_LEN:
                    weight_queue.append(data)
                else:
                    print(f"잘못된 데이터 길이: {data}")
                    continue

                for i in range(ARR_LEN):
                    # 등록 모드 로직
                    if not inventory[i]:
                        registration_detection(inventory, i, product_name)
                        if registration_successful:
                            # 등록 완료 시 이벤트 비활성화하고 함수 종료
                            # register_event.clear()
                            register_event = False
                            return

            time.sleep(0.2)

    except serial.SerialException as e:
        print(f"시리얼 연결 문제 발생: {e}")
        time.sleep(1)
    finally:
        # ser.close()
        # register_event.clear()
        register_event = False

def start_delete_monitoring(ser):
    """삭제 모드에서 로드셀 데이터 모니터링 함수 (항상 동작)"""
    global register_event
    
    time.sleep(1)  # 시리얼 초기화 대기

    try:
        while True:
            # 등록 모드가 실행 중일 때는 대기
            if register_event:
                time.sleep(1)
                continue

            inventory = get_inventory(refrigerator_id, ARR_LEN)
            # register_event.wait()
            
            if ser.in_waiting > 0:    
                data = list(map(int, ser.readline().decode('utf-8').strip().split()))

                if len(data) == ARR_LEN:
                    weight_queue.append(data)
                else:
                    print(f"잘못된 데이터 길이: {data}")
                    continue

                for i in range(ARR_LEN):
                    # 삭제 모드 로직
                    if inventory[i]:
                        removal_detection(inventory, i)

            time.sleep(1)

    except serial.SerialException as e:
        print(f"시리얼 연결 문제 발생: {e}")
        time.sleep(1)
    finally:
        ser.close()

        
# if __name__ == "__main__":
#     start_weight_data_monitoring(False, ser)