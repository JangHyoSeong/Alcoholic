import re, random
from fuzzywuzzy import fuzz
from jamo import h2j, j2hcj


def preprocess_text(text):
    text = re.sub(r'[^a-zA-Z0-9가-힣\s]', '', text)
    return text.lower().strip()


def text_to_jamo(text):
    # 한글을 자모 단위로 변환하고, 영어와 숫자는 그대로 반환
    jamo_text = j2hcj(h2j(text))
    return jamo_text


def calculate_levenshtein_similarity(text1, text2):
    custom_text1 = text_to_jamo(preprocess_text(text1))
    custom_text2 = text_to_jamo(preprocess_text(text2))
    return fuzz.ratio(custom_text1, custom_text2) / 100


def find_most_similar_product(text, product_data):
    # custom_text = text_to_jamo(preprocess_text(text))
    highest_similarity = 0
    best_match = "Undefined"

    for product in product_data:
        en_name = product["enDrinkName"]
        kr_name = product["krDrinkName"]

        en_similarity = calculate_levenshtein_similarity(text, en_name)
        kr_similarity = calculate_levenshtein_similarity(text, kr_name)

        if en_similarity > highest_similarity:
            highest_similarity = en_similarity
            best_match = en_name
        if kr_similarity > highest_similarity:
            highest_similarity = kr_similarity
            best_match = kr_name
            
    # print(best_match, highest_similarity)
    return best_match, highest_similarity