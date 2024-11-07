import sys
sys.stdin = open('input.txt')

import sys
# # input = sys.stdin.readline
# # sys.setrecursionlimit(10**4)

import re, random
# from sklearn.feature_extraction.text import CountVectorizer
# from sklearn.metrics.pairwise import cosine_similarity
from fuzzywuzzy import fuzz

def preprocess_text(text):
    # 불필요한 기호나 공백 제거, 소문자로 변환
    text = re.sub(r'[^a-zA-Z0-9가-힣\s]', '', text)
    return text.lower().strip()


# def calculate_cosine_similarity(text1, text2):
#     # CountVectorizer로 텍스트 벡터화
#     vectorizer = CountVectorizer().fit_transform([text1, text2])
#     vectors = vectorizer.toarray()
#     return cosine_similarity(vectors)[0][1]

def calculate_levenshtein_similarity(text1, text2):
    # Levenshtein 유사도 계산
    return fuzz.ratio(text1, text2) / 100  # 비율로 변환
def find_most_similar_product(text, product_names):
    custom_text = preprocess_text(text)
    best_match = None
    highest_similarity = 0
    highest_cos = 0
    highest_lev_sim = 0
    lst = []

    for product_name in product_names:
        # 제품명을 전처리하여 유사도 계산
        processed_product_name = preprocess_text(product_name)
        # cosine_sim = calculate_cosine_similarity(ocr_text, processed_product_name)
        levenshtein_sim = calculate_levenshtein_similarity(custom_text, processed_product_name)

        # 두 유사도를 가중평균으로 조정 (가중치 예시)
        # similarity = (cosine_sim * 0.6) + (levenshtein_sim * 0.4)
        similarity = levenshtein_sim

        # print(cosine_sim, levenshtein_sim, similarity)
        # 최고 유사도 제품 선택
        if similarity > highest_similarity:
            highest_similarity = similarity
            best_match = product_name
            # highest_cos = cosine_sim
            highest_lev_sim = levenshtein_sim
            lst.clear()
            lst.append(product_name)
        elif similarity == highest_similarity:
            lst.append(product_name)

    return best_match, highest_similarity, lst

# 제품명 더미 데이터 생성 함수
def generate_dummy_products(num_products, brands, names, grades):
    product_names = []
    for _ in range(num_products):
        brand = random.choice(brands)
        name = random.choice(names)
        grade = random.choice(grades)

        # product_name = f"{brand} {name} {grade} {random.randint(1, 30)}-Year"
        product_name = f"{brand} {name} {grade}"
        product_names.append(product_name)
    return product_names


if __name__ == "__main__":
    brands = ["Jack Daniels", "아브라카", "Chivas Regal", "Johnnie Walker", "Glenfiddich", "Ballantine's", "Jameson", "Crown Royal"]
    names = ["Ace", "Beta", "Coco", "Daily", "다브라", "Flight", "Greate", "High", "Ice", "Jacky", "King"]
    grades = ["A", "B", "C", "D", "E", "F", "S"]

    # 예시 DB에서 가져온 제품명 리스트
    product_names_db = generate_dummy_products(10000, brands, names, grades)


    # OCR 결과 텍스트 예시
    # ocr_text = "Jac Dainel dail King S asdfasdft lkj charile wild enhoy wow holy shit what the fuck you marathin shit"
    # ocr_text = "Jac Dainel King S"
    ocr_text = "아브라카 다브라"
    print(preprocess_text(ocr_text))


    # 유사도 높은 제품 찾기
    best_product, similarity_score, matched_product = find_most_similar_product(ocr_text, product_names_db)
    for product in matched_product:
        print(f"최고 유사 제품: {product}, 유사도: {similarity_score:.2f}")
    print(preprocess_text("Coffee & Time TUMS BLACK Have a nice day"))