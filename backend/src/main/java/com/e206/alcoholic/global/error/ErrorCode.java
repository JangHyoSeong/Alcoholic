// ErrorCode.java
package com.e206.alcoholic.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

// 에러 코드 열거형
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다.");

    private final HttpStatus status; // HTTP 상태 코드
    private final String message; // 에러 메시지
}