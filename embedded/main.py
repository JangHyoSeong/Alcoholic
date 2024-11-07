import time
from OCR import run_ocr

def main():
    try:
        # while True:
        print("OCR process started")
        run_ocr()
    except KeyboardInterrupt:
        print("Program interrupted by user.")

if __name__ == "__main__":
    main()
