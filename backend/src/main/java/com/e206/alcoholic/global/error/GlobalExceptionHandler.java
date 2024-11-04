package com.e206.alcoholic.global.error;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult(); // 검증 오류가 발생한 필드와 메시지를 가지고 있는 객체
        String message = result.getFieldError() != null ?
                result.getFieldError().getDefaultMessage() : // "아이디는 필수 입력값입니다"
                ErrorCode.INVALID_INPUT_VALUE.getMessage();  // "잘못된 입력값입니다."
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)  // 400 에러 반환
                .body(new ErrorResponse(message));
    }

    // 사용자 정의 예외
    @ExceptionHandler(CustomException.class)  // CustomException이 발생했을 때 이 메서드가 처리하도록 지정
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        // ErrorCode enum에 따라 적절한 HTTP 상태 코드를 결정합니다
        HttpStatus status;
        // switch문을 사용해 각 에러 코드별로 적절한 HTTP 상태 코드 매핑
        switch (e.getErrorCode()) {
            case USER_NOT_FOUND:         // 사용자를 찾을 수 없는 경우
            case REFRIGERATOR_NOT_FOUND: // 냉장고를 찾을 수 없는 경우
                status = HttpStatus.NOT_FOUND;  // 404: 요청한 리소스가 서버에 없음
                break;
            case DUPLICATE_USERNAME:         // 중복된 사용자 이름인 경우
            case DUPLICATE_SERIAL_NUMBER:    // 중복된 시리얼 번호인 경우
                status = HttpStatus.CONFLICT;  // 409: 리소스 충돌 (이미 존재하는 데이터)
                break;
            default:
                status = HttpStatus.BAD_REQUEST;  // 400: 잘못된 요청
        }
        // ResponseEntity를 사용해 상태 코드와 에러 메시지를 함께 반환
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(e.getErrorCode().getMessage()));
    }

    // 잘못된 HTTP 메소드 ( 405 Method Not Allowed )
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED.getMessage()));
    }

    // 인증되지 않은 사용자 ( 401 Unauthorized )
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ErrorCode.UNAUTHORIZED.getMessage()));
    }

    // 접근 권한 없음 ( 403 Forbidden )
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ErrorCode.FORBIDDEN.getMessage()));
    }

    // 리소스를 찾을 수 없음 ( 404 Not Found )
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ErrorCode.RESOURCE_NOT_FOUND.getMessage()));
    }

    // 잘못된 자격 증명 ( 401 Unauthorized )
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ErrorCode.UNAUTHORIZED.getMessage()));
    }

    // 리소스 충돌 ( 409 Conflict )
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        if (e.getMessage() != null && e.getMessage().contains("duplicate")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(ErrorCode.CONFLICT.getMessage()));
        }
        return handleAllException(e);
    }

    // 서버 내부 오류 ( 500 Internal Server Error )
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}