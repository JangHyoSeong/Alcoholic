package com.e206.alcoholic.domain.cocktail.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 칵테일 목록 조회를 위한 DTO 클래스
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CocktailListResponseDto {
    private Integer id;
    private String enCocktailName;  // 칵테일 영문 이름
    private String krCocktailName;  // 칵테일 한글 이름
    private String image;           // 칵테일 이미지 URL
    private String instruction;     // 칵테일 제조 방법
}