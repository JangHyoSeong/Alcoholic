import cv2
import requests
from matplotlib import pyplot as plt
from datetime import datetime
import json
import base64
from dotenv import load_dotenv
import os
import search
import productList
import sys
import yolo_detect
from langdetect import detect, DetectorFactory
sys.stdout.reconfigure(encoding='utf-8')

DetectorFactory.seed = 0

# env
load_dotenv()

secret_key = os.getenv("SECRET_KEY")
api_url = os.getenv("OCR_API_URL")


def capture_image(cap, filename="captured_image.jpg"):
    # cap = cv2.VideoCapture(0)

    # 잠깐 텀 두기
    for _ in range(5):
        cap.read()
        
    ret, frame = cap.read()
    if ret:
        cv2.imwrite(filename, frame)
        print(f"image capture: {filename}")
        # show_image(filename)
    else:
        print("image capture fail")
    # cap.release()


def show_image(filename):
    image = cv2.imread(filename)
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    plt.imshow(image_rgb)
    plt.axis('off')  
    plt.show()


def encode_image_to_base64(image_path):
    with open(image_path, "rb") as image_file:
        base64_encoded = base64.b64encode(image_file.read()).decode("utf-8")
    return base64_encoded

# OCR API
def call_ocr_api(image_path):
    image_data = encode_image_to_base64(image_path)

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
        # return extract_text_from_result(result)
        # return extract_large_text(result, 20)
        return filter_large_texts(result, 0.5)
    else:
        print("OCR fail:", response.status_code, response.text)
        return None


def extract_large_text(ocr_result, min_height=15):
    large_texts = []
    for field in ocr_result['images'][0]['fields']:
        vertices = field['boundingPoly']['vertices']
        height = abs(vertices[0]['y'] - vertices[2]['y'])  # 텍스트 높이 계산

        # 높이가 기준치 이상인 경우에만 텍스트 추가
        if height >= min_height:
            large_texts.append(field['inferText'])

    return " ".join(large_texts)


def filter_large_texts(ocr_data, threshold_ratio=0.7):
    fields = ocr_data['images'][0]['fields']

    if not fields:
        print("No fields detected in OCR data.")
        return ""

    heights = [abs(field['boundingPoly']['vertices'][0]['y'] - field['boundingPoly']['vertices'][2]['y']) for field in fields]
    
    if not heights:
        print("No valid bounding boxes detected in fields.")
        return ""

    max_height = max(heights)
    threshold_height = max_height * threshold_ratio
    
    # Filter texts based on the threshold
    large_texts = [field['inferText'] for field, height in zip(fields, heights) if height >= threshold_height]
    
    return " ".join(large_texts)


def extract_text_from_result(result):
    texts = []
    for field in result.get("images", []):
        for infer_result in field.get("fields", []):
            texts.append(infer_result.get("inferText"))
    return " ".join(texts)

    
def run_ocr(cap):
    image_path = "captured_image.jpg"

    # 파일 존재 여부 확인
    if not os.path.exists(image_path):
        print("Image file does not exist. Exiting OCR process.")
        return None
        
    capture_image(cap, image_path)

    text = call_ocr_api(image_path)
    if text:
        # try:
        #     language = detect(text)
            # if language not in ['ko', 'en']:  # 한국어('ko')와 영어('en')가 아닌 경우
            #     print("Detected language is not Korean or English. Exiting OCR process.")
            #     return None
        # except Exception as e:
        #     print("Language detection failed:", e)
        #     return None

        print("OCR result:\n", text)
        product_data = productList.get_products()

        matched_product, similarity_score = search.find_most_similar_product(text, product_data)
        # for product in matched_product:
        #     # print(f"best product: {product}, similarity: {similarity_score:.2f}")
        #     print(f"best product: {product}")

        # YOLO 모델로 술 병 탐지
        yolo_product, yolo_score = yolo_detect.detect_bottle(image_path)

        if yolo_score > 0.85:
            return yolo_product
        if similarity_score < 0.2: # 너무 낮으면 다시 촬영하게...
            return None
        return matched_product
    else:
        print("text make fail")
        return None
        
    
if __name__ == "__main__":
    run_ocr()
