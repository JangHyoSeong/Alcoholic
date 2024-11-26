package com.e206.alcoholic.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknameRequestDto {
    @NotBlank(message = "닉네임은 필수 입력값입니다")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$",
            message = "닉네임은 2~20자의 한글, 영문, 숫자만 사용 가능합니다")
    private String nickname; // 변경할 닉네임
}