package com.e206.alcoholic.domain.refrigerator.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorUpdateRequestDto {

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$",
            message = "냉장고 이름은 2~20자의 한글, 영문, 숫자만 사용 가능합니다")
    private String name;
}