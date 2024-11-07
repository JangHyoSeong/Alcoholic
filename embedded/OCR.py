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
        print(f"image capture: {filename}")
        show_image(filename)
    else:
        print("image capture fail")
    cap.release()

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
        return extract_text_from_result(result)
    else:
        print("OCR fail:", response.status_code, response.text)
        return None


def extract_text_from_result(result):
    texts = []
    for field in result.get("images", []):
        for infer_result in field.get("fields", []):
            texts.append(infer_result.get("inferText"))
    return " ".join(texts)


def run_ocr():
    image_path = "captured_image.jpg"
    capture_image(image_path)

    text = call_ocr_api(image_path)
    if text:
        print("OCR result:\n", text)
        product_data = productList.get_products()

        matched_product, similarity_score = search.find_most_similar_product(text, product_data)
        for product in matched_product:
            print(f"best product: {product}, similarity: {similarity_score:.2f}")
    else:
        print("text make fail")
        

if __name__ == "__main__":
    run_ocr()