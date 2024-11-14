import tkinter as tk
import customtkinter as ctk
from tkinter import messagebox
from PIL import Image, ImageTk
import os

os.environ["DISPLAY"] = ":0"

class GUI:
    def __init__(self, root, start_ocr_callback, manual_entry_callback, confirm_registration_callback):
        self.root = root
        self.start_ocr_callback = start_ocr_callback
        self.manual_entry_callback = manual_entry_callback
        self.confirm_registration_callback = confirm_registration_callback

        self.root.title("주류 냉장고 등록")
        # self.root.attributes("-fullscreen", True)  # 전체 화면으로 설정

        # 상태 표시 라벨
        self.status_label = ctk.CTkLabel(root, text="주류 냉장고에 제품을 등록하세요", font=("Arial", 15))
        self.status_label.pack(pady=20)

        # "등록하기" 버튼
        self.register_button = ctk.CTkButton(root, text="등록하기", command=self.start_ocr_callback, font=("Arial", 15), width=250, height=50)
        self.register_button.pack(pady=30)

        # OCR 결과 화면 요소
        self.captured_image_label = ctk.CTkLabel(root, text="")
        self.product_label = ctk.CTkLabel(root, text="", font=("Arial", 15))

        # OCR 후 버튼들
        self.confirm_button = ctk.CTkButton(root, text="예", command=lambda: self.confirm_registration_callback(self.product_label.cget("text")), width=250, height=50, font=("Arial", 15))
        self.retake_button = ctk.CTkButton(root, text="다시 촬영하기", command=self.start_ocr_callback, width=250, height=50, font=("Arial", 15))
        self.manual_button = ctk.CTkButton(root, text="직접 등록하기", command=self.manual_entry_callback, width=250, height=50, font=("Arial", 15))

        # 수기 입력 화면 요소
        self.manual_entry = ctk.CTkEntry(root, font=("Arial", 15), width=250)
        self.submit_button = ctk.CTkButton(root, text="등록", command=self.submit_manual_entry, width=250, height=50, font=("Arial", 15))


    def show_result_screen(self, product_name):
        self.register_button.pack_forget()  # "등록하기" 버튼 숨김

        try:
            sample_image = Image.open("captured_image.jpg")  # 이미지 파일 경로
            self.captured_img = ctk.CTkImage(sample_image, size=(200, 200))
            self.captured_image_label.configure(image=self.captured_img)
            self.captured_image_label.pack(pady=20)
        except Exception as e:
            print(f"이미지 로드 오류: {e}")

        if product_name is not None:
            self.status_label.configure(text=f"{product_name} 등록하시겠습니까?")
            self.product_label.configure(text=product_name)
        # "예", "다시 촬영하기", "직접 등록하기" 버튼 표시
            self.confirm_button.pack(pady=10)
            self.retake_button.pack(pady=10)
            self.manual_button.pack(pady=10)

        else:
            self.status_label.configure(text="다시 촬영해주세요")
            self.retake_button.pack(pady=10)
        
        self.retake_button.configure(state="normal")
        self.root.update_idletasks()

    def hide_result_screen(self):
        self.product_label.pack_forget()
        self.confirm_button.pack_forget()
        self.retake_button.pack_forget()
        self.manual_button.pack_forget()

    # def confirm_registration(self):
    #     messagebox.showinfo("등록", "제품이 등록되었습니다.")

    def submit_manual_entry(self):
        product_name = self.manual_entry.get()
        if product_name:
            messagebox.showinfo("등록", f"{product_name}를 등록합니다.")
        else:
            messagebox.showwarning("입력 오류", "제품명을 입력해 주세요.")

    def manual_entry_screen(self):
        self.status_label.configure(text="제품명을 입력하세요:")
        self.product_label.pack_forget()
        self.captured_image_label.pack_forget()
        self.confirm_button.pack_forget()
        self.retake_button.pack_forget()
        self.manual_button.pack_forget()
        self.manual_entry.pack(pady=20)
        self.submit_button.pack(pady=20)

    def reset_to_initial_state(self):
        self.status_label.configure(text="주류 냉장고에 제품을 등록하세요")
        self.captured_image_label.pack_forget()
        self.product_label.pack_forget()
        self.confirm_button.pack_forget()
        self.retake_button.pack_forget()
        self.manual_button.pack_forget()
        self.register_button.pack(pady=30)
