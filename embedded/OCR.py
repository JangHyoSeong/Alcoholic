import cv2
import requests
from matplotlib import pyplot as plt
from datetime import datetime
import json
import base64
from dotenv import load_dotenv
import os
import search
import sys
sys.stdout.reconfigure(encoding='utf-8')

# env
load_dotenv()

secret_key = os.getenv("SECRET_KEY")
api_url = os.getenv("OCR_API_URL")

def capture_image(filename="captured_image.jpg"):
    cap = cv2.VideoCapture(0)
    ret, frame = cap.read()
    if ret:
        cv2.imwrite(filename, frame)
        print(f"이미지 캡처 완료: {filename}")
        # show_image(filename)
    else:
        print("이미지 캡처 실패")
    cap.release()
    # cv2.destroyAllWindows()

def show_image(filename):
    image = cv2.imread(filename)
    # BGR 형식을 RGB로 변환 (OpenCV는 기본적으로 BGR 형식을 사용)
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    plt.imshow(image_rgb)
    plt.axis('off')  # 축 제거
    plt.show()

# 이미지 파일을 base64 인코딩
def encode_image_to_base64(image_path):
    with open(image_path, "rb") as image_file:
        base64_encoded = base64.b64encode(image_file.read()).decode("utf-8")
    return base64_encoded

# OCR API 호출
def call_ocr_api(image_path):
    # 이미지 base64로 인코딩
    image_data = encode_image_to_base64(image_path)
    # 요청 본문 구성
    payload = {
        "images": [
            {
                "format": "jpg",
                "name": "medium",
                "data": image_data,
            }
        ],
        "lang": "ko",
        "requestId": "sample_id",
        "resultType": "string",
        "timestamp": int(datetime.now().timestamp() * 1000),
        "version": "V1"
    }

    headers = {
        'Content-Type': "application/json",
        'X-OCR-SECRET': secret_key
    }

    # files = {"image": open(image_path, "rb")}
    response = requests.post(api_url, headers=headers, data=json.dumps(payload))

    if response.status_code == 200:
        result = response.json()
        return extract_text_from_result(result)
    else:
        print("OCR 요청 실패:", response.status_code, response.text)
        return None

# OCR 결과에서 텍스트 추출
def extract_text_from_result(result):
    texts = []
    for field in result.get("images", []):
        for infer_result in field.get("fields", []):
            texts.append(infer_result.get("inferText"))
    return " ".join(texts)

# 메인 실행 함수
def main():
    image_path = "captured_image.jpg"
    capture_image(image_path)

    # OCR 호출 횟수 제한
    sys.stdin.flush()
    command = input(f"OCR을 실행하려면 'y'를 입력하세요.")

    # if command.lower() == 'y':
    text = call_ocr_api(image_path)
    if text:
        print("OCR 결과:\n", text)
        product_names_db = search.generate_dummy_products(10000, brands, names, grades)

        best_product, similarity_score, matched_product = search.find_most_similar_product(text, product_names_db)
        for product in matched_product:
            print(f"최고 유사 제품: {product}, 유사도: {similarity_score:.2f}")
    else:
        print("텍스트 추출 실패")
    # else:
    #     print("다시 입력하세요.")
    # print("최대 호출 횟수 도달, 프로그램 종료.")


brands = ["Coffee Time", "진로 골드", "Chivas Regal", "Johnnie Walker", "Glenfiddich", "Ballantine's", "Jameson", "Crown Royal"]
names = ["TUMS BLACK", "Beta", "Coco", "Daily", "Emotion", "Flight", "Greate", "High", "Ice", "Jacky", "King"]
grades = ["Have a nice day", "B", "C", "D", "E", "F", "S"]

if __name__ == "__main__":
    main()