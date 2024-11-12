package com.e206.alcoholic.domain.cocktail.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CocktailInventoryResponseDto {
    private List<CocktailInventoryDto> result;  // 내부 DTO 클래스 사용
}
