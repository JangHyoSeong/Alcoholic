package com.e206.alcoholic.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 닉네임 수정 요청
@Getter
@NoArgsConstructor
public class NicknameRequestDto {
    private String nickname;
}