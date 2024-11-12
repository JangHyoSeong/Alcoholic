import customtkinter as ctk
import threading
import time
from gui import GUI
from loadcell import start_weight_data_monitoring, register_drink
from OCR import run_ocr
import serial

# 전역 시리얼 포트 설정 (단일 인스턴스로 유지)
ser = serial.Serial('/dev/ttyACM0', 9600, timeout=1)

def start_ocr():
    """OCR을 실행하고 결과를 GUI에 전달"""
    gui.status_label.configure(text="라벨 인식 중...")
    app.update()

    product_name = run_ocr()
    gui.show_result_screen(product_name)

def manual_entry():
    """수기 입력 화면으로 전환"""
    gui.manual_entry_screen()

def confirm_registration(product_name):
    """'예' 버튼 눌렀을 때 등록 과정 실행"""
    gui.status_label.configure(text="등록 대기 중...")
    gui.hide_result_screen()

    # 30초 동안 무게 감지 모니터링 (등록 대기 상태)
    register_thread = threading.Thread(target=start_weight_data_monitoring, args=(True, ser), daemon=True)
    register_thread.start()

    start_time = time.time()
    while time.time() - start_time < 30:
        # 스레드가 종료된 경우 (무게 감지로 등록이 완료됨)
        if not register_thread.is_alive():
            register_drink(1, "test_drink", 1, "captured_image.jpg")
            gui.status_label.configure(text=f"{product_name} 등록 완료")
            return  # 함수 종료

        app.update_idletasks()
        time.sleep(0.1)

    gui.status_label.configure(text="등록 실패: 제품을 올리지 않았습니다.")
    
def start_gui():
    """GUI 초기화 및 실행"""
    global app, gui
    app = ctk.CTk()
    gui = GUI(app, start_ocr, manual_entry, confirm_registration)

    # DELETE 모니터링 모드는 백그라운드 스레드에서 항상 실행
    delete_monitor_thread = threading.Thread(target=start_weight_data_monitoring, args=(False, ser), daemon=True)
    delete_monitor_thread.start()

    app.mainloop()

def main():
    """메인 함수 - 앱 실행 흐름 관리"""
    ctk.set_appearance_mode("dark")  # 다크 모드 설정
    ctk.set_default_color_theme("blue")  # 기본 테마 색상 설정

    start_gui()

if __name__ == "__main__":
    main()
