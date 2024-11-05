package com.e206.alcoholic.global.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpDto {
    private String username;
    private String password;
    private String nickname;
}
