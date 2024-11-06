package com.e206.alcoholic.domain.drinks.dto;

import com.e206.alcoholic.domain.drinks.entity.Drink;
import lombok.Builder;
import lombok.Getter;

// 목록 조회 응답 DTO
@Getter
@Builder
public class DrinkListResponseDto {
    private Integer id;
    private Integer categoryId;
    private String enDrinkName;
    private String krDrinkName;

    public static DrinkListResponseDto from(Drink drink) {
        return DrinkListResponseDto.builder()
                .id(drink.getId())
                .categoryId(drink.getCategoryId())
                .enDrinkName(drink.getEnDrinkName())
                .krDrinkName(drink.getKrDrinkName())
                .build();
    }
}