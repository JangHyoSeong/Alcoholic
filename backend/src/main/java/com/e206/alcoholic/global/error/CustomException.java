package com.e206.alcoholic.global.error;

import lombok.Getter;

// 커스텀 예외 클래스
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    // ErrorCode enum으로부터 에러 메시지를 받아 예외를 생성
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}