package com.e206.alcoholic.domain.cocktail.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CocktailResponseDto {
    private Integer id;
    private String enCocktailName;
    private String krCocktailName;
    private Integer value;
    private String image;
    private String instruction;
    private List<String> alcoholCategoriesName;  // Category 엔티티의 categoryName들이 저장됨
}