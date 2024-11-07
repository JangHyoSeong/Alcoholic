import re, random
from fuzzywuzzy import fuzz

def preprocess_text(text):
    text = re.sub(r'[^a-zA-Z0-9가-힣\s]', '', text)
    return text.lower().strip()


def calculate_levenshtein_similarity(text1, text2):
    return fuzz.ratio(text1, text2) / 100


def find_most_similar_product(text, product_data):
    custom_text = preprocess_text(text)
    highest_similarity = 0
    best_match = []

    for product in product_data:
        en_name = product["enDrinkName"]
        kr_name = product["krDrinkName"]

        en_similarity = calculate_levenshtein_similarity(custom_text, preprocess_text(en_name))
        kr_similarity = calculate_levenshtein_similarity(custom_text, preprocess_text(kr_name))

        similarity = max(en_similarity, kr_similarity)

        if similarity > highest_similarity:
            highest_similarity = similarity
            best_match.clear()
            best_match.append(product)
        elif similarity == highest_similarity:
            best_match.append(product)

    return best_match, highest_similarity