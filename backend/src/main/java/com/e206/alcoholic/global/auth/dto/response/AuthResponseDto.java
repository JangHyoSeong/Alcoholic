package com.e206.alcoholic.global.auth.dto.response;

import com.e206.alcoholic.domain.user.entity.User;
import lombok.Getter;

@Getter
public class AuthResponseDto {
    private final String username; // 사용자 아이디
    private final String nickname; // 닉네임

    public AuthResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}