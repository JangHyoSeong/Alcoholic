package com.e206.alcoholic.domain.cocktail.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CocktailCreateRequestDto {
    private String enCocktailName;
    private String krCocktailName;
    private String instruction;
    private List<IngredientRequestDto> ingredients;

    @Getter
    @Builder
    public static class IngredientRequestDto {
        private Integer categoryId;    // 재료 카테고리 ID
        private String ingredient;     // 재료 이름
        private String measure;        // 재료 양
    }
}