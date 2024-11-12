import serial
import time
import json
from dotenv import load_dotenv
from collections import deque
import os
import requests


load_dotenv()
server_api_url = os.getenv("SERVER_API_URL")
token = os.getenv("TOKEN")

weight_queue = deque(maxlen=3)
weight_cnt = [0,0,0,0]
ARR_LEN = 4
DETECTION_THRESHOLD = 2
refrigerator_id = 1


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


def start_weight_data_monitoring():
    """로드셀 데이터 모니터링 함수"""
    ser = serial.Serial('/dev/ttyACM0', 9600, timeout=1)
    time.sleep(2)  # 시리얼 초기화 대기

    inventory = get_inventory(refrigerator_id, ARR_LEN)
    
    THRESHOLD_WEIGHT = 200

    try:
        while True:
            if ser.in_waiting > 0:
                data = list(map(int, ser.readline().decode('utf-8').strip().split()))
                weight_queue.append(data)

                for i in range(ARR_LEN):
                    if not inventory[i]:
                        if weight_queue[-1][i] >= THRESHOLD_WEIGHT and weight_cnt[i] < DETECTION_THRESHOLD:
                            weight_cnt[i] += 1
                        else:
                            weight_cnt[i] = 0
                        if weight_cnt[i] == DETECTION_THRESHOLD:
                            ## POST 등록
                            register_drink(refrigerator_id, "test_drink", i+1, "captured_image.jpg")
                            inventory[i] = True
                    elif inventory[i]:
                        if weight_queue[-1][i] < THRESHOLD_WEIGHT and weight_cnt[i] > 0:
                            weight_cnt[i] -= 1
                        else:
                            weight_cnt[i] = DETECTION_THRESHOLD
                        if weight_cnt[i] == 0:
                            ## delete 제거
                            delete_drink(refrigerator_id, i+1)
                            inventory[i] = False

            time.sleep(1)
    
    except KeyboardInterrupt:
        print("프로그램 종료")
    finally:
        ser.close()

if __name__ == "__main__":
    start_weight_data_monitoring()