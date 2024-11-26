package com.e206.alcoholic.domain.stock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrinkStockDetailsResponseDto {
    private Integer stockId;
    private String name;
    private String koreanName;
    private Float degree;
    private String type;
    private Integer position;
    private LocalDateTime stockTime;
}
