package com.e206.alcoholic.domain.refrigerator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDto {
    private String serialNumber; // 냉장고 시리얼 번호
}