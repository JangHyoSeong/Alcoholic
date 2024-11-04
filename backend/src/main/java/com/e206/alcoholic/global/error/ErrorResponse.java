// ErrorResponse
package com.e206.alcoholic.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorResponse {
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
}