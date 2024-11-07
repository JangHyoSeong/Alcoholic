package com.e206.alcoholic.domain.cocktail.dto.response;

import com.e206.alcoholic.domain.ingredient.dto.IngredientDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

// 칵테일 상세 정보를 반환하기 위한 DTO 클래스
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CocktailDetailResponseDto {
    private Integer id;
    private String enCocktailName;  // 칵테일 영문 이름
    private String krCocktailName;  // 칵테일 한글 이름
    private String image;           // 칵테일 이미지 URL
    private String instruction;     // 칵테일 제조 방법
    private List<IngredientDto> ingredients;  // 칵테일 재료 목록
}