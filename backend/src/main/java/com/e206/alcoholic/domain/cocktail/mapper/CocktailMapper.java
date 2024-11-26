package com.e206.alcoholic.domain.cocktail.mapper;

import com.e206.alcoholic.domain.cocktail.dto.response.CocktailDetailResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailInventoryDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailResponseDto;
import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import com.e206.alcoholic.domain.category.entity.Category;
import com.e206.alcoholic.domain.ingredient.mapper.IngredientMapper;

public class CocktailMapper {
    // 칵테일 상세 정보를 DTO로 변환
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

    // 칵테일 목록 조회용 DTO로 변환 (알코올 카테고리 이름 포함)
    public static CocktailInventoryDto toCocktailInventoryDto(Cocktail cocktail) {  // 반환 타입 수정
        return CocktailInventoryDto.builder()  // builder 이름 수정
                .id(cocktail.getId())
                .enCocktailName(cocktail.getEnCocktailName())
                .krCocktailName(cocktail.getKrCocktailName())
                .value(cocktail.getValue())
                .image(cocktail.getImage())
                .instruction(cocktail.getInstruction())
                .alcoholCategoriesName(
                        cocktail.getIngredients().stream()
                                .map(ingredient -> ingredient.getCategory())
                                .filter(category -> {
                                    Integer categoryId = category.getId();
                                    return categoryId != null && categoryId >= 1 && categoryId <= 12;
                                })
                                .map(Category::getCategoryName)
                                .distinct()
                                .toList()
                )
                .build();
    }
}