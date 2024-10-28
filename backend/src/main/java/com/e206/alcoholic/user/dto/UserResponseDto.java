package com.e206.alcoholic.user.dto;

import com.e206.alcoholic.user.entity.User;
import lombok.Getter;

// 유저 정보 응답
@Getter
public class UserResponseDto {
    private final String username;
    private final String nickname;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}