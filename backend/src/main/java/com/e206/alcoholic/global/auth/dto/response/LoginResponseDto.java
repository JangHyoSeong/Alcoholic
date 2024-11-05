package com.e206.alcoholic.global.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String token; // JWT 토큰
    private String username; // 사용자 아이디
}