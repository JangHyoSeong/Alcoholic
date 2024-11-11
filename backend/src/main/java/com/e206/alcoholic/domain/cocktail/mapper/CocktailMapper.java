package com.e206.alcoholic.domain.cocktail.mapper;

import com.e206.alcoholic.domain.cocktail.dto.response.CocktailDetailResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailResponseDto;
import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import com.e206.alcoholic.domain.ingredient.mapper.IngredientMapper;

public class CocktailMapper {
    public static CocktailDetailResponseDto toCocktailDetailDto(Cocktail cocktail) {
        return CocktailDetailResponseDto.builder()
                .id(cocktail.getId())
                .enCocktailName(cocktail.getEnCocktailName())
                .krCocktailName(cocktail.getKrCocktailName())
                .image(cocktail.getImage())
                .instruction(cocktail.getInstruction())
                .ingredients(cocktail.getIngredients().stream()
                        .map(IngredientMapper::toDto)
                        .toList())
                .build();
    }

    // Cocktail 엔티티를 목록 조회용 DTO로 변환하는 메서드
    public static CocktailResponseDto toCocktailListDto(Cocktail cocktail) {
        return CocktailResponseDto.builder()
                .id(cocktail.getId())
                .enCocktailName(cocktail.getEnCocktailName())
                .krCocktailName(cocktail.getKrCocktailName())
                .value(cocktail.getValue())
                .image(cocktail.getImage())
                .instruction(cocktail.getInstruction())
                .build();
    }
}