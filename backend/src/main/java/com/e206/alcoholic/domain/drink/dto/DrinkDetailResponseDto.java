package com.e206.alcoholic.domain.drink.dto;

import com.e206.alcoholic.domain.drink.entity.Drink;
import lombok.Builder;
import lombok.Getter;

// 상세 조회 응답 DTO
@Getter
@Builder
public class DrinkDetailResponseDto {
    private Integer id;
    private Integer categoryId;
    private String enDrinkName;
    private String krDrinkName;
    private Float alcoholDegree;
    private String description;

    public static DrinkDetailResponseDto from(Drink drink) {
        return DrinkDetailResponseDto.builder()
                .id(drink.getId())
                .categoryId(drink.getCategory().getId())
                .enDrinkName(drink.getEnDrinkName())
                .krDrinkName(drink.getKrDrinkName())
                .alcoholDegree(drink.getAlcoholDegree())
                .description(drink.getDescription())
                .build();
    }
}
