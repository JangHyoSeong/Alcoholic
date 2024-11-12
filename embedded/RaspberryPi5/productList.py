import requests
import json
from dotenv import load_dotenv
import os

# .env 파일 로드
load_dotenv()

# 서버 URL과 토큰 설정
server_api_url = os.getenv("SERVER_API_URL")
# token = os.getenv("TOKEN")  # .env 파일에 저장된 토큰을 불러옵니다.

# 헤더 설정
# headers = {
#     "Authorization": f"Bearer {token}",  # 토큰이 포함된 Authorization 헤더
#     "Content-Type": "application/json"   # JSON 형식
# }

def get_products():
    try:
        # response = requests.get(f"{url}/drinks", headers=headers)
        response = requests.get(f"{server_api_url}/drinks")
        response.raise_for_status()  # HTTP 오류 발생 시 예외 발생
        data = response.json().get("result", [])       # JSON 데이터를 딕셔너리로 변환
        return data
    except requests.exceptions.RequestException as e:
        print("요청 중 오류 발생:", e)
        return []