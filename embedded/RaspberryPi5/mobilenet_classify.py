import cv2
from tensorflow.keras.models import load_model

# MobileNet 모델 로드
mobilenet_model = load_model('wine_classifier.pth')  # MobileNet 모델 파일 경로를 지정해야 합니다.

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
