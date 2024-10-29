// UserResponseDto.java
package com.e206.alcoholic.domain.user.dto.response;

import com.e206.alcoholic.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String username; // 사용자 아이디
    private final String nickname; // 사용자 닉네임

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}