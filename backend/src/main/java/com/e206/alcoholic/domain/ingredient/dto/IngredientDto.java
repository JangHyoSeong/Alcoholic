package com.e206.alcoholic.domain.ingredient.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 칵테일 재료 정보를 전달하기 위한 DTO 클래스
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class IngredientDto {
    private Integer id;
    private Integer cocktailId;  // 해당 재료가 속한 칵테일의 ID
    private Integer categoryId;  // 재료의 카테고리 ID
    private String ingredient;// 재료 이름
    private String measure;   // 재료의 양/단위
}