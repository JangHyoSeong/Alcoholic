package com.e206.alcoholic.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;

    // ErrorCode enum으로부터 에러 메시지를 받아 생성
    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    // 직접 에러 메시지를 받아 생성
    public ErrorResponse(String message) {
        this.message = message;
    }
}