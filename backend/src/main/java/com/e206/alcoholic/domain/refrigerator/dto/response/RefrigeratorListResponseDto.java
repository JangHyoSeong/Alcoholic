package com.e206.alcoholic.domain.refrigerator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorListResponseDto {
    private List<RefrigeratorResponseDto> results;
}