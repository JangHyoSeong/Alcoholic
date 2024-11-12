package com.e206.alcoholic.domain.cocktail.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CocktailInventoryDto {  // 새로운 내부 DTO 클래스
    private Integer id;
    private String enCocktailName;
    private String krCocktailName;
    private Integer value;
    private String image;
    private String instruction;
    private List<String> alcoholCategoriesName;
}