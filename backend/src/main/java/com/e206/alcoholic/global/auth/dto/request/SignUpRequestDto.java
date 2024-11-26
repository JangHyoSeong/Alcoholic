package com.e206.alcoholic.global.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "아이디는 필수 입력값입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$",
            message = "아이디는 8~20자의 영문 대/소문자, 숫자만 사용 가능합니다")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$",
            message = "비밀번호는 8~20자의 영문 대/소문자, 숫자만 사용 가능합니다")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력값입니다")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$",
            message = "닉네임은 2~20자의 한글, 영문, 숫자만 사용 가능합니다")
    private String nickname;
}