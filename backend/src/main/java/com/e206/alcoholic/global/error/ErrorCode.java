package com.e206.alcoholic.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum ErrorCode {
    /* 400 Bad Request: 잘못된 요청 관련 */
    INVALID_INPUT_VALUE("잘못된 입력값입니다."),
    INVALID_TYPE_VALUE("잘못된 타입이 입력되었습니다."),

    /* 401 Unauthorized: 인증 관련 */
    UNAUTHORIZED("인증되지 않은 사용자입니다."),

    /* 403 Forbidden: 권한 관련 */
    FORBIDDEN("접근 권한이 없는 사용자입니다."),

    /* 404 Not Found: 리소스를 찾을 수 없는 경우 */
    RESOURCE_NOT_FOUND("요청한 리소스를 찾을 수 없습니다."),
    USER_NOT_FOUND("존재하지 않는 아이디입니다."),
    REFRIGERATOR_NOT_FOUND("존재하지 않는 냉장고입니다."),
    DRINK_NOT_FOUND("해당 ID의 술을 찾을 수 없습니다."),

    /* 405 Method Not Allowed: HTTP 메서드 관련 */
    METHOD_NOT_ALLOWED("잘못된 HTTP 메서드입니다."),

    /* 409 Conflict: 리소스 충돌 관련 */
    CONFLICT("리소스 충돌이 발생했습니다."),
    DUPLICATE_USERNAME("이미 존재하는 아이디입니다."),
    DUPLICATE_SERIAL_NUMBER("이미 등록된 냉장고입니다."),

    /* 500 Internal Server Error: 서버 내부 오류 */
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다."),

    /* Custom Error: 특정 비즈니스 로직 관련 에러 */
    MAIN_REFRIGERATOR_DELETE_DENIED("메인 냉장고는 삭제할 수 없습니다.");

    private String message;
}