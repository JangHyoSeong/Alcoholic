package com.e206.alcoholic.domain.ingredient.mapper;

import com.e206.alcoholic.domain.ingredient.dto.IngredientDto;
import com.e206.alcoholic.domain.ingredient.entity.Ingredient;

public class IngredientMapper {
    // Ingredient 엔티티를 DTO로 변환하는 메서드
    public static IngredientDto toDto(Ingredient ingredient) {
        return IngredientDto.builder()
                .id(ingredient.getId())                           // 재료 ID
                .cocktailId(ingredient.getCocktailId().getId())   // 해당 재료가 속한 칵테일의 ID
                .categoryId(ingredient.getCategoryId().getId())   // 재료의 카테고리 ID
                .ingredient(ingredient.getIngredient())           // 재료 이름
                .measure(ingredient.getMeasure())                 // 재료의 양/단위
                .build();
    }
}