package com.e206.alcoholic.domain.refrigerator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorResponseDto {
    private Integer id;
    private String name;
    private Boolean isMain;
}
