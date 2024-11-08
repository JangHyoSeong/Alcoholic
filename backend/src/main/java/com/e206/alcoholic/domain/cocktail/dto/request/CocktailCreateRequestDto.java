package com.e206.alcoholic.domain.cocktail.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CocktailCreateRequestDto {
    private String enCocktailName;     // 칵테일 영문 이름
    private String krCocktailName;     // 칵테일 한글 이름
    private String image;              // 칵테일 이미지 URL
    private String instruction;        // 칵테일 제조 방법
    private Integer userId;            // 사용자 ID
    private List<IngredientRequestDto> ingredients;  // 재료 목록

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class IngredientRequestDto {
        private Integer categoryId;    // 재료 카테고리 ID
        private String ingredient;     // 재료 이름
        private String measure;        // 재료 양
    }
}