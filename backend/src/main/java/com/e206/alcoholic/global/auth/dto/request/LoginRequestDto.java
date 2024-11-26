package com.e206.alcoholic.global.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "아이디는 필수 입력값입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$",
            message = "아이디는 8~20자의 영문 대/소문자, 숫자만 사용 가능합니다")
    private String username; // 로그인 아이디

    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    private String password; // 비밀번호
}