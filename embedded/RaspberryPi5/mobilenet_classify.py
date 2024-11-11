import cv2
import torch
from torchvision import models, transforms

mobilenet_model = torch.load('wine_classifier.pth')
mobilenet_model.eval()  # 평가 모드로 설정
def classify_bottle(bottle_img):
    """
    MobileNet 모델을 사용하여 잘라낸 이미지의 술 종류를 분류
    """
    # 이미지 전처리
    bottle_img = cv2.resize(bottle_img, (224, 224))
    bottle_img = bottle_img / 255.0
    bottle_img = bottle_img.reshape((1, 224, 224, 3))
    
    # MobileNet 모델로 분류 수행
    prediction = mobilenet_model.predict(bottle_img)
    class_label = prediction.argmax()  # 예측된 클래스 라벨 반환
    
    return class_label
