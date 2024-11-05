package com.e206.alcoholic.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

// 에러 코드 열거형
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 유저 에러 코드
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),

    // 냉장고 에러 코드
    REFRIGERATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 냉장고이거나 접근 권한이 없습니다."),
    DUPLICATE_SERIAL_NUMBER(HttpStatus.CONFLICT, "이미 등록된 냉장고입니다."),
    MAIN_REFRIGERATOR_DELETE_DENIED(HttpStatus.BAD_REQUEST, "메인 냉장고는 삭제할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}