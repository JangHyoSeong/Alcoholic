import serial
import time

# 시리얼 포트와 통신 속도 설정
ser = serial.Serial('/dev/ttyACM0', 9600, timeout=1)
time.sleep(2) # 시리얼 초기화 대기

def weight_data():
    try:
        while True:
            if ser.in_waiting > 0:
                data = list(map(int, ser.readline().decode('utf-8').strip().split()))             
                print(data)

    except KeyboardInterrupt:
        print("프로그램 종료")
    finally:
        ser.close()

if __name__ == "__main__":
    weight_data()