import cv2
import torch
from ultralytics import YOLO

# YOLO 모델 로드
yolo_model = YOLO('./best.pt')  # YOLOv8 모델 파일 경로를 지정해야 합니다.

def detect_bottle(image_path):
    """
    YOLO 모델을 사용하여 술 병을 탐지하고 좌표를 반환
    """
    img = cv2.imread(image_path)
    
    # 탐지 수행
    results = model(img, conf=0.9)

    # 신뢰도 임계값 설정
    threshold = 0.9
    if len(results[0].boxes) == 0:
        print("탐지 결과: Unknown")
    # 탐지 결과 확인 및 후처리
    for result in results[0].boxes:  # 첫 번째 이미지의 탐지 결과
        confidence = result.conf.item()  # 신뢰도
        class_id = int(result.cls.item())  # 클래스 ID

        if confidence < threshold:  # 신뢰도가 낮으면 "unknown"
            print("탐지 결과: Unknown")
        else:  # 신뢰도가 높으면 클래스명 출력
            class_name = model.names[class_id]  # 클래스 이름
            print(f"탐지된 객체: {class_name}, 신뢰도: {confidence:.2f}")
