import tkinter as tk
import customtkinter as ctk
from tkinter import messagebox
from PIL import Image, ImageTk
import os
import cv2 
import subprocess

os.environ["DISPLAY"] = ":0"

class GUI:
    def __init__(self, root, start_ocr_callback, manual_entry_callback, confirm_registration_callback, shared_cap):
        self.root = root
        self.start_ocr_callback = start_ocr_callback
        self.manual_entry_callback = manual_entry_callback
        self.confirm_registration_callback = confirm_registration_callback
        self.cap = shared_cap

        self.root.title("주류 냉장고 등록")

        # 홈 버튼 프레임 및 버튼 (맨 위 왼쪽 고정)
        self.home_button_frame = ctk.CTkFrame(root, width=50, height=50)  # 프레임 정의
        self.home_button_frame.place(x=10, y=10)  # 맨 위 왼쪽에 고정

        # 홈 아이콘 로드
        home_icon = Image.open("home_icon.png")  # 아이콘 크기 조정
        self.home_icon_image = ctk.CTkImage(home_icon, size=(30, 30))

        # 홈 버튼 생성
        self.home_button = ctk.CTkButton(
            self.home_button_frame, 
            image=self.home_icon_image,  # 아이콘 추가
            text="",  # 텍스트 제거
            command=self.go_home, 
            width=40, 
            height=40
        )
        self.home_button.pack()

        # 상태 표시 라벨
        self.status_label = ctk.CTkLabel(root, text="카메라에 제품 라벨이 보이도록 위치시킨 후, [등록하기] 버튼을 눌러주세요.", font=("Arial", 15))
        self.status_label.pack(pady=20)

        # 실시간 카메라 및 결과를 담을 프레임
        self.preview_frame = ctk.CTkFrame(root)
        self.preview_frame.pack()

        # 실시간 카메라 영상
        self.camera_preview_label = ctk.CTkLabel(self.preview_frame, text="")
        self.camera_preview_label.grid(row=0, column=0, padx=10, pady=10)

        # OCR 결과 이미지
        self.captured_image_label = ctk.CTkLabel(self.preview_frame, text="")
        self.captured_image_label.grid(row=0, column=1, padx=10, pady=10)
        self.captured_image_label.grid_forget()

        # "등록하기" 버튼
        self.register_button = ctk.CTkButton(root, text="등록하기", command=self.start_ocr_callback, font=("Arial", 15), width=250, height=50)
        self.register_button.pack(pady=10)
        

        # OCR 결과 화면 요소
        # self.captured_image_label = ctk.CTkLabel(root, text="")
        self.product_label = ctk.CTkLabel(root, text="", font=("Arial", 15))

        # OCR 후 버튼들
        self.confirm_button = ctk.CTkButton(root, text="예", command=lambda: self.confirm_registration_callback(self.product_label.cget("text")), width=250, height=50, font=("Arial", 15))
        self.retake_button = ctk.CTkButton(root, text="다시 촬영하기", command=self.start_ocr_callback, width=250, height=50, font=("Arial", 15))
        self.manual_button = ctk.CTkButton(root, text="직접 등록하기", command=self.manual_entry_callback, width=250, height=50, font=("Arial", 15))
        self.manual_button.pack(pady=10)

        # 수기 입력 화면 요소
        self.manual_entry = ctk.CTkEntry(root, font=("Arial", 15), width=250)
        self.submit_button = ctk.CTkButton(root, text="등록", command=self.submit_manual_entry, width=250, height=50, font=("Arial", 15))

        self.show_camera_preview()


    def show_camera_preview(self):
        # 카메라에서 프레임을 읽고 Tkinter에 표시
        ret, frame = self.cap.read()
        self.confirm_button.configure(text="예", state="normal")
        self.retake_button.configure(text="다시 촬영하기", state="normal")

        if not ret or frame is None:  # 프레임 읽기 실패 처리
            print("카메라 프레임을 읽을 수 없습니다.")
            self.status_label.configure(text="카메라 연결 실패")
            self.retake_button.pack(pady=10)
            return

        frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)  # OpenCV는 BGR 포맷이므로 RGB로 변환
        img = Image.fromarray(frame)
        self.captured_img = ctk.CTkImage(img, size=(200, 200))

        self.camera_preview_label.configure(image=self.captured_img)
        self.camera_preview_label.image = self.captured_img
        self.root.after(10, self.show_camera_preview)


    # def stop_camera_preview(self):
    #     # self.cap.release()  # 카메라 장치 해제
    #     self.camera_preview_label.pack_forget()
    #     # self.camera_preview_label.configure(image=None)
    #     # self.camera_preview_label.image = None


    def show_result_screen(self, product_name):
        self.camera_preview_label.grid(row=0, column=0, padx=10, pady=10)
        self.home_button.pack()
        self.register_button.pack_forget()  # "등록하기" 버튼 숨김
        # self.stop_camera_preview()
        try:
            sample_image = Image.open("captured_image.jpg")  # 이미지 파일 경로
            self.captured_img = ctk.CTkImage(sample_image, size=(200, 200))
            self.captured_image_label.configure(image=self.captured_img)
            self.captured_image_label.grid(row=0, column=1, padx=10, pady=10)
            # self.captured_image_label.pack(pady=20)
        except Exception as e:
            print(f"이미지 로드 오류: {e}")

        if product_name:
            self.status_label.configure(text=f"{product_name} 등록하시겠습니까?")
            self.product_label.configure(text=product_name)
        # "예", "다시 촬영하기", "직접 등록하기" 버튼 표시
            self.confirm_button.configure(text="예", state="normal")
            self.retake_button.configure(text="다시 촬영하기", state="normal")

            self.confirm_button.pack(pady=10)
            self.retake_button.pack(pady=10)
            self.manual_button.pack(pady=10)

        else:
            self.status_label.configure(text="다시 촬영해주세요")
            self.retake_button.configure(text="다시 촬영하기", state="normal")
            self.retake_button.pack(pady=10)
        
        self.root.update_idletasks()

    def hide_result_screen(self):
        self.product_label.pack_forget()
        self.confirm_button.pack_forget()
        self.retake_button.pack_forget()
        self.manual_button.pack_forget()

    def retake_capture(self):
        self.home_button.pack()
        self.captured_image_label.grid_forget()  # "등록하기" 버튼 숨김
        self.status_label.configure(text="다시 촬영해주세요.")
        self.retake_button.configure(text="다시 촬영하기", state="normal")
        self.retake_button.pack(pady=10)

    def confirm_registration(self):
        messagebox.showinfo("등록", "제품이 등록되었습니다.")

    def submit_manual_entry(self):
        product_name = self.manual_entry.get()
        if product_name:
            messagebox.showinfo("등록", f"{product_name}를 등록합니다.")
        else:
            messagebox.showwarning("입력 오류", "제품명을 입력해 주세요.")

    def manual_entry_screen(self):
        self.home_button.pack()
        self.status_label.configure(text="제품명을 입력하세요:")
        self.product_label.pack_forget()
        self.register_button.pack_forget()
        self.captured_image_label.pack_forget()
        self.confirm_button.pack_forget()
        self.retake_button.pack_forget()
        self.manual_button.pack_forget()
        self.manual_entry.pack(pady=20)
        self.submit_button.pack(pady=20)
        self.camera_preview_label.grid_forget()
        self.captured_image_label.grid(row=0, column=1, padx=10, pady=10)

        # subprocess.Popen(["matchbox-keyboard"])
        # subprocess.Popen(["florence"])
        # subprocess.Popen(["onboard"])
    
    def go_home(self):
        """홈 화면으로 이동"""
        self.reset_to_initial_state()


    def reset_to_initial_state(self):
        self.status_label.configure(text="카메라에 제품 라벨이 보이도록 위치시킨 후, [등록하기] 버튼을 눌러주세요.")
        self.camera_preview_label.grid(row=0, column=0, padx=10, pady=10)
        self.captured_image_label.grid_forget()

        self.product_label.pack_forget()
        self.confirm_button.pack_forget()
        self.retake_button.pack_forget()
        # self.manual_button.pack_forget()
        self.manual_entry.pack_forget()
        self.submit_button.pack_forget()
        self.confirm_button.configure(text="예", state="normal")
        self.retake_button.configure(text="다시 촬영하기", state="normal")

        self.home_button.pack()
        self.register_button.pack(pady=10)
        self.manual_button.pack(pady=10)
        # self.show_camera_preview()
