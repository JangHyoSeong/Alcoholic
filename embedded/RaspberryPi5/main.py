import customtkinter as ctk
import threading
import time
from gui import GUI
from loadcell import start_registration_monitoring, start_delete_monitoring, register_drink
from OCR import run_ocr
import serial
import cv2 

# 전역 시리얼 포트 설정 (단일 인스턴스로 유지)
ser = serial.Serial('/dev/ttyACM0', 9600, timeout=1)

def start_ocr(cap):
    """OCR을 실행하고 결과를 GUI에 전달"""
    gui.camera_preview_label.grid(row=0, column=0, padx=10, pady=10)
    gui.register_button.pack_forget()
    gui.confirm_button.pack_forget()
    gui.retake_button.pack_forget()
    gui.manual_button.pack_forget()

    gui.home_button.pack_forget()

    gui.status_label.configure(text="라벨 인식 중...")
    app.update_idletasks()  # 필요한 부분만 갱신
    
    try:
        product_name = run_ocr(cap)
        if not product_name:
            gui.home_button.pack()
            gui.captured_image_label.grid_forget()  # "등록하기" 버튼 숨김
            gui.status_label.configure(text="다시 촬영해주세요.")
            gui.retake_button.configure(text="다시 촬영하기", state="normal")
            gui.retake_button.pack(pady=10)
            # gui.show_camera_preview()
        else:
            gui.show_result_screen(product_name)
    except Exception as e:
        print("Error during OCR process:", e)
        gui.home_button.pack()
        gui.captured_image_label.grid_forget()  # "등록하기" 버튼 숨김
        gui.status_label.configure(text="다시 촬영해주세요")
        gui.retake_button.configure(text="다시 촬영하기", state="normal")
        gui.retake_button.pack(pady=10)
        # gui.show_camera_preview()
    # gui.show_result_screen(product_name)

def manual_entry():
    """수기 입력 화면으로 전환"""
    gui.manual_entry_screen()

def confirm_registration(product_name):
    """'예' 버튼 눌렀을 때 등록 과정 실행"""
    gui.camera_preview_label.grid_forget()
    gui.home_button.pack_forget()
    gui.status_label.configure(text="등록 대기 중...")
    gui.hide_result_screen()

    # 등록 취소 플래그
    cancel_registration = threading.Event()

    # 등록 취소하기 버튼 추가
    def cancel():
        """등록 취소 버튼 클릭 시 실행"""
        cancel_registration.set()  # 취소 플래그 설정
        gui.status_label.configure(text="등록이 취소되었습니다.")
        gui.root.after(2000, lambda: gui.show_result_screen(product_name))  # 2초 후 이전 화면으로

    cancel_button = ctk.CTkButton(
        gui.root,
        text="등록 취소하기",
        command=cancel,
        font=("Arial", 15),
        width=250,
        height=50
    )
    cancel_button.pack(pady=10)

    # 등록 프로세스 실행 (별도 스레드)
    def registration_process():
        """등록 과정 실행"""
        register_thread = threading.Thread(target=start_registration_monitoring, args=(ser, product_name), daemon=True)
        register_thread.start()

        start_time = time.time()
        while time.time() - start_time < 30:
            # 등록 취소 여부 확인
            if cancel_registration.is_set():
                cancel_button.pack_forget()  # 취소 버튼 숨기기
                return  # 함수 종료

            # 스레드가 종료된 경우 (무게 감지로 등록이 완료됨)
            if not register_thread.is_alive():
                gui.status_label.configure(text=f"{product_name} 등록 완료")
                cancel_button.pack_forget()  # 취소 버튼 숨기기
                gui.root.after(3000, gui.reset_to_initial_state)
                return  # 함수 종료

            time.sleep(0.1)

        # 타임아웃: 등록 실패
        gui.status_label.configure(text="등록 실패: 제품을 올리지 않았습니다.")
        cancel_button.pack_forget()  # 취소 버튼 숨기기
        gui.root.after(3000, gui.reset_to_initial_state)

    # 등록 프로세스 스레드 실행
    threading.Thread(target=registration_process, daemon=True).start()


def make_fullscreen():
    """윈도우를 전체 화면 모드로 변경"""
    app.attributes("-fullscreen", True)

def start_gui():
    """GUI 초기화 및 실행"""
    global app, gui
    app = ctk.CTk()

    cap = cv2.VideoCapture(0)
    gui = GUI(app, lambda: start_ocr(cap), manual_entry, confirm_registration, cap)
    app.after(100, make_fullscreen)  # 100ms 후에 전체 화면으로 설정

    # DELETE 모니터링 모드는 백그라운드 스레드에서 항상 실행
    delete_monitor_thread = threading.Thread(target=start_delete_monitoring, args=(ser,), daemon=True)
    delete_monitor_thread.start()

    app.mainloop()

def main():
    """메인 함수 - 앱 실행 흐름 관리"""
    ctk.set_appearance_mode("dark")  # 다크 모드 설정
    ctk.set_default_color_theme("blue")  # 기본 테마 색상 설정

    start_gui()

if __name__ == "__main__":
    main()
