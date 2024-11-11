import cv2
import torch
from ultralytics import YOLO

# YOLO 모델 로드
yolo_model = YOLO('best.pt')  # YOLOv8 모델 파일 경로를 지정해야 합니다.

def detect_bottle(image_path):
    """
    YOLO 모델을 사용하여 술 병을 탐지하고 좌표를 반환
    """
    img = cv2.imread(image_path)
    results = yolo_model(img)
    
    # 탐지된 병의 좌표 목록을 저장
    bottle_coords = []
    for result in results:
        x1, y1, x2, y2 = result['bbox']  # 탐지된 병의 좌표 추출
        bottle_coords.append((x1, y1, x2, y2))
    
    return bottle_coords
