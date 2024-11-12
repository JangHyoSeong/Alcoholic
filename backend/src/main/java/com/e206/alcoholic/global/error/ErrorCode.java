package com.e206.alcoholic.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /* 글로벌 예외 */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "잘못된 타입이 입력되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP 메서드입니다."),
    CONFLICT(HttpStatus.CONFLICT, "리소스 충돌이 발생했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    /* 유저 */
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없는 사용자입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),

    /* 냉장고 */
    REFRIGERATOR_DONT_HAVE(HttpStatus.NOT_FOUND, "보유하지 않는 냉장고입니다."),
    REFRIGERATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 냉장고입니다."),
    DUPLICATE_SERIAL_NUMBER(HttpStatus.CONFLICT, "이미 등록된 냉장고입니다."),
    MAIN_REFRIGERATOR_DELETE_DENIED(HttpStatus.BAD_REQUEST, "메인 냉장고는 삭제할 수 없습니다."),

    /* 술 */
    DRINK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 술을 찾을 수 없습니다."),

    /* 재고 */
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 재고가 없습니다"),
    STOCK_NOT_IN_USER_REFRIGERATORS(HttpStatus.FORBIDDEN, "본인이 보유하지 않은 술입니다."),
    ALREADY_IN_POSITION(HttpStatus.BAD_REQUEST, "해당 위치에 이미 재고가 존재합니다."),

    /* 칵테일 */
    COCKTAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "칵테일이 존재하지 않습니다."),

    /* 카테고리 */
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리가 존재하지 않습니다."),

    /* AWS S3 관련 에러  */
    IMAGE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "이미지가 존재하지 않습니다");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}