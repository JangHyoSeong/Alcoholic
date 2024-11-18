package com.e206.alcoholic.domain.cocktail.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CocktailCreateRequestDto {
    @Pattern(regexp = "^[A-Za-z0-9\\s]{1,50}$", message = "칵테일 영어 이름은 영어, 숫자만 가능합니다.")
    private String enCocktailName;

    @Pattern(regexp = "^[가-힣0-9\\s]{1,50}$", message = "칵테일 한국 이름은 한국어, 숫자만 가능합니다")
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