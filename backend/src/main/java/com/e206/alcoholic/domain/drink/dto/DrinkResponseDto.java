package com.e206.alcoholic.domain.drink.dto;

import com.e206.alcoholic.domain.drink.entity.Drink;
import lombok.Builder;
import lombok.Getter;

// 목록 조회 응답 DTO
@Getter
@Builder
public class DrinkResponseDto {
    private Integer id;
    private Integer categoryId;
    private String categoryName;
    private String enDrinkName;
    private String krDrinkName;

    public static DrinkResponseDto from(Drink drink) {
        return DrinkResponseDto.builder()
                .id(drink.getId())
                .categoryId(drink.getCategory().getId())
                .categoryName(drink.getCategory().getCategoryName())
                .enDrinkName(drink.getEnDrinkName())
                .krDrinkName(drink.getKrDrinkName())
                .build();
    }
}