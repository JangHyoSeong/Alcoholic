package com.e206.alcoholic.domain.cocktail.dto.response;

import com.e206.alcoholic.domain.ingredient.dto.IngredientDto;
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
public class CocktailDetailResponseDto {
    private Integer id;
    private String enCocktailName;
    private String krCocktailName;
    private String image;
    private String instruction;
    private List<IngredientDto> ingredients;
}