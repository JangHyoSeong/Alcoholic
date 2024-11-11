package com.e206.alcoholic.domain.stock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrinkStockResponseDto {
    private Integer id;
    private String name;
    private String koreanName;
    private LocalDateTime stockTime;
    private Integer position;
    private String imageUrl;
}
