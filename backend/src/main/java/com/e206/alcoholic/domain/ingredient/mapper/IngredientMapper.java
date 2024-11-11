package com.e206.alcoholic.domain.ingredient.mapper;

import com.e206.alcoholic.domain.ingredient.dto.IngredientDto;
import com.e206.alcoholic.domain.ingredient.entity.Ingredient;

public class IngredientMapper {
    public static IngredientDto toDto(Ingredient ingredient) {
        return IngredientDto.builder()
                .id(ingredient.getId())
                .cocktailId(ingredient.getCocktail().getId())
                .categoryId(ingredient.getCategory().getId())
                .ingredient(ingredient.getIngredientName())
                .measure(ingredient.getMeasure())
                .build();
    }
}