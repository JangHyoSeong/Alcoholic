package com.e206.alcoholic.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknameRequestDto {
    private String nickname; // 변경할 닉네임
}