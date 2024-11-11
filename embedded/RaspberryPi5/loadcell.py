import serial
import time
import json
from dotenv import load_dotenv
from collections import deque
import os
import requests
from OCR import encode_image_to_base64
import base64

load_dotenv()
server_api_url = os.getenv("SERVER_API_URL")
token = os.getenv("TOKEN")

weight_queue = deque(maxlen=3)
weight_cnt = [0,0,0,0]
ARR_LEN = 4
refrigerator_id = 1

# def encode_image_to_base64(image_path):
#     with open(image_path, "rb") as image_file:
#         base64_encoded = base64.b64encode(image_file.read()).decode("utf-8")
#     return base64_encoded


def post_register(refrigerator_id, drink_name, position, image_path):
    url = f"{server_api_url}/refrigerators/admin/{refrigerator_id}"
    headers = {
        "Authorization": token,
        # "Content-Type": "multipart/form-data"
    }
    # files = {"image": open(image_path, "rb")}  # 이미지 파일을 바이너리 형식으로 열기
    image_base64 = encode_image_to_base64(image_path)

    data = {
        "drinkName": drink_name,
        "position": position,
        # "image": ("image.jpg", open(image_path, "rb"), "image/jpeg")
        # "image": image_base64
    }

    try:
        # response = requests.post(url, headers=headers, files=files, data=data)
        response = requests.post(url, headers=headers, data=data)
        response.raise_for_status()  # HTTP 오류가 발생하면 예외 발생

        # JSON 응답 파싱
        result = response.json().get("result")
        print("POST 요청 성공:", response)
        return result

    except requests.exceptions.RequestException as e:
        print("POST 요청 실패:", e)
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
    
    THRESHOLD_WEIGHT = 300

    try:
        while True:
            if ser.in_waiting > 0:
                data = list(map(int, ser.readline().decode('utf-8').strip().split()))
                weight_queue.append(data)

                for i in range(ARR_LEN):
                    if not inventory[i]:
                        if weight_queue[-1][i] >= THRESHOLD_WEIGHT and weight_cnt[i] < 3:
                            weight_cnt[i] += 1
                        else:
                            weight_cnt[i] = 0
                        if weight_cnt[i] == 3:
                            # print(f"{i+1}번 센서 술 등록") 
                            print(f"2번 센서 술 등록") 
                            ## POST 등록
                            # post_register(refrigerator_id, "test_drink", i+1, "captured_image.jpg")
                            post_register(refrigerator_id, "test_drink", 2, "captured_image.jpg")
                            inventory[i] = True
                    elif inventory[i]:
                        if weight_queue[-1][i] < THRESHOLD_WEIGHT and weight_cnt[i] > 0:
                            weight_cnt[i] -= 1
                        else:
                            weight_cnt[i] = 3
                        if weight_cnt[i] == 0:
                            print(f"{i+1}번 센서 술 제거")
                            ## POST 제거
                            inventory[i] = False

            time.sleep(1)
    
    except KeyboardInterrupt:
        print("프로그램 종료")
    finally:
        ser.close()

if __name__ == "__main__":
    start_weight_data_monitoring()