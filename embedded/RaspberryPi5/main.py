import customtkinter as ctk
from gui import GUI
from OCR import run_ocr

def start_ocr():
    # OCR을 실행하고 결과를 GUI에 전달
    gui.status_label.configure(text="라벨 인식 중...")
    app.update()
    
    product_name = run_ocr()
    gui.show_result_screen(product_name)

def manual_entry():
    # 수기 입력 화면으로 전환
    gui.manual_entry_screen()

def main():
    # GUI 초기화 및 실행
    ctk.set_appearance_mode("dark")  # 다크 모드 설정
    ctk.set_default_color_theme("blue")  # 기본 테마 색상 설정

    global app, gui
    app = ctk.CTk()
    gui = GUI(app, start_ocr, manual_entry)

    app.mainloop()

if __name__ == "__main__":
    main()
