package com.e206.alcoholic.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserResponseDto {
    private final Integer userId;
    private final String username; // 사용자 아이디
    private final String nickname; // 사용자 닉네임
}