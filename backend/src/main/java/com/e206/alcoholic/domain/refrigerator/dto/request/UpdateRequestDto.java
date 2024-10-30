package com.e206.alcoholic.domain.refrigerator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDto {
    private String name; // 변경할 냉장고 이름
}