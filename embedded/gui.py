import tkinter as tk
import customtkinter as ctk
from tkinter import messagebox
import subprocess
from PIL import Image, ImageTk  # 이미지를 표시하기 위한 라이브러리
import time
from OCR import run_ocr

import os
os.environ["DISPLAY"] = ":0"

class GUI:
    def __init__(self, root, start_ocr_callback, manual_entry_callback):
        self.root = root
        self.start_ocr_callback = start_ocr_callback
        self.manual_entry_callback = manual_entry_callback

        self.root.title("주류 냉장고 등록")
        self.root.geometry("400x500")

        # 상태 표시 라벨
        self.status_label = ctk.CTkLabel(root, text="주류 냉장고에 제품을 등록하세요", font=("Arial", 16))
        self.status_label.pack(pady=10)

        # "등록하기" 버튼
        self.register_button = ctk.CTkButton(root, text="등록하기", command=self.start_ocr_callback, font=("Arial", 14))
        self.register_button.pack(pady=20)

        # 기본 이미지 설정 (가상의 이미지 사용)
        # sample_image = Image.open("captured_image.jpg")  # 실제 이미지 경로 지정
        # sample_image = sample_image.resize((200, 200))
        # self.captured_img = ImageTk.PhotoImage(sample_image)

        # OCR 결과 화면 요소
        self.captured_image_label = ctk.CTkLabel(root)
        self.product_label = ctk.CTkLabel(root, text="", font=("Arial", 14))

        # OCR 후 버튼들
        self.confirm_button = ctk.CTkButton(root, text="예", command=self.confirm_registration, width=100)
        self.retake_button = ctk.CTkButton(root, text="다시 촬영하기", command=self.start_ocr_callback, width=100)
        self.manual_button = ctk.CTkButton(root, text="직접 등록하기", command=self.manual_entry_callback, width=100)

        # 수기 입력 화면 요소
        self.manual_entry = ctk.CTkEntry(root, font=("Arial", 12))
        self.submit_button = ctk.CTkButton(root, text="등록", command=self.submit_manual_entry, width=100)

    def show_result_screen(self, product_name):
        # OCR 결과 화면 전환
        self.register_button.pack_forget()  # "등록하기" 버튼 숨김
        self.status_label.configure(text=f"{product_name}를 등록하시겠습니까?")
        self.product_label.configure(text=product_name)
        self.product_label.pack(pady=10)  # 제품명 라벨 표시
        # self.captured_image_label.configure(image=self.captured_img)
        # self.captured_image_label.pack(pady=10)  # 이미지 표시
        
        # "예", "다시 촬영하기", "직접 등록하기" 버튼 표시
        self.confirm_button.pack(pady=5)
        self.retake_button.pack(pady=5)
        self.manual_button.pack(pady=5)

        # 강제로 화면 갱신
        self.root.update_idletasks()

    def confirm_registration(self):
        messagebox.showinfo("등록", "제품이 등록되었습니다.")

    def submit_manual_entry(self):
        product_name = self.manual_entry.get()
        if product_name:
            messagebox.showinfo("등록", f"{product_name}를 등록합니다.")
        else:
            messagebox.showwarning("입력 오류", "제품명을 입력해 주세요.")

    def manual_entry_screen(self):
        # 수기 입력 화면으로 전환
        self.status_label.configure(text="제품명을 입력하세요:")
        self.product_label.pack_forget()
        self.captured_image_label.pack_forget()
        self.confirm_button.pack_forget()
        self.retake_button.pack_forget()
        self.manual_button.pack_forget()
        self.manual_entry.pack(pady=10)
        self.submit_button.pack(pady=10)