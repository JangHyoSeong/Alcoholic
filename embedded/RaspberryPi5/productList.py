import requests
import json
from dotenv import load_dotenv
import os

# .env 파일 로드
load_dotenv()

server_api_url = os.getenv("SERVER_API_URL")

def get_products():
    try:
        response = requests.get(f"{server_api_url}/drinks")
        response.raise_for_status()  # HTTP 오류 발생 시 예외 발생
        data = response.json()
        return data
    except requests.exceptions.RequestException as e:
        print("요청 중 오류 발생:", e)
        return []
