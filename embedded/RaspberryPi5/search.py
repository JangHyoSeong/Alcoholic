import re, random
from fuzzywuzzy import fuzz
from sklearn.feature_extraction.text import CountVectorizer
from jamo import h2j, j2hcj
import numpy as np


def preprocess_text(text):
    text = re.sub(r'[^a-zA-Z0-9가-힣\s]', '', text)
    return text.lower().strip()


def text_to_jamo(text):
    # 한글을 자모 단위로 변환하고, 영어와 숫자는 그대로 반환
    jamo_text = j2hcj(h2j(text))
    return jamo_text


def custom_text(text):
    return text_to_jamo(preprocess_text(text))


def calculate_levenshtein_similarity(text1, text2):
    return fuzz.ratio(text1, text2) / 100


def calculate_jaccard_similarity(text1, text2):
    vectorizer = CountVectorizer(analyzer='char', ngram_range=(2, 3))
    ngrams1 = vectorizer.fit_transform([text1])
    ngrams2 = vectorizer.transform([text2])
    
    intersection = np.minimum(ngrams1.toarray(), ngrams2.toarray()).sum()
    union = np.maximum(ngrams1.toarray(), ngrams2.toarray()).sum()
    return intersection / union


def find_most_similar_product(text, product_data):
    highest_similarity = 0
    best_match = None

    for product in product_data:
        en_name = product["enDrinkName"]
        kr_name = product["krDrinkName"]

        customed_text = custom_text(text)
        customed_en_name = custom_text(en_name)
        customed_kr_name = custom_text(kr_name)


        # en_similarity = calculate_levenshtein_similarity(customed_text, customed_en_name)
        # kr_similarity = calculate_levenshtein_similarity(customed_text, customed_kr_name)
        en_similarity = calculate_jaccard_similarity(customed_text, customed_en_name)
        kr_similarity = calculate_jaccard_similarity(customed_text, customed_kr_name)

        if en_similarity > highest_similarity:
            highest_similarity = en_similarity
            best_match = en_name
        if kr_similarity > highest_similarity:
            highest_similarity = kr_similarity
            best_match = kr_name
            
    print(best_match, highest_similarity)
    return best_match, highest_similarity