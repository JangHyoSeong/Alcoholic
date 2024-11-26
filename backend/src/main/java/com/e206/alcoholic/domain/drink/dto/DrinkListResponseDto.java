package com.e206.alcoholic.domain.drink.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DrinkListResponseDto {
    private List<DrinkResponseDto> result;
}
