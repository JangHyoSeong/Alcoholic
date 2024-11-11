package com.e206.alcoholic.domain.cocktail.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CocktailListResponseDto {
    private List<CocktailResponseDto> result;
}
