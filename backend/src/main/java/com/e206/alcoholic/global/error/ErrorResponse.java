package com.e206.alcoholic.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.List;

@Getter
public class ErrorResponse {
    private final String message;
    private List<FieldError> fieldErrors;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.message = message != null ? message : errorCode.getMessage();
    }

    public void addFieldErrors(BindingResult bindingResult) {
        fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> new FieldError(
                        error.getField(),
                        error.getDefaultMessage()))
                .toList();
    }

    @Getter
    @AllArgsConstructor
    public static class FieldError {

        private final String field;
        private final String message;
    }
}