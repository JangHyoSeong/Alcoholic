package com.e206.alcoholic.domain.cocktail.mapper;

import com.e206.alcoholic.domain.cocktail.dto.CocktailDetailResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.CocktailListResponseDto;
import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import com.e206.alcoholic.domain.ingredient.mapper.IngredientMapper;

import java.util.stream.Collectors;

public class CocktailMapper {
    // Cocktail 엔티티를 상세 정보 DTO로 변환하는 메서드
    public static CocktailDetailResponseDto toCocktailDetailDto(Cocktail cocktail) {
        return CocktailDetailResponseDto.builder()
                .id(cocktail.getId())
                .enCocktailName(cocktail.getEnCocktailName())
                .krCocktailName(cocktail.getKrCocktailName())
                .image(cocktail.getImage())
                .instruction(cocktail.getInstruction())
                // 칵테일에 포함된 재료들을 DTO로 변환하여 리스트로 수집
                .ingredients(cocktail.getIngredients().stream()
                        .map(IngredientMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    // Cocktail 엔티티를 목록 조회용 DTO로 변환하는 메서드
    public static CocktailListResponseDto toCocktailListDto(Cocktail cocktail) {
        return CocktailListResponseDto.builder()
                .id(cocktail.getId())
                .enCocktailName(cocktail.getEnCocktailName())
                .krCocktailName(cocktail.getKrCocktailName())
                .image(cocktail.getImage())
                .instruction(cocktail.getInstruction())
                .build();
    }
}