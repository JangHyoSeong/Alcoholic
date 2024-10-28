package com.e206.alcoholic.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 회원가입 요청
@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String password;
    private String nickname;
}