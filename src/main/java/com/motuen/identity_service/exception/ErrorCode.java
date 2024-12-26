package com.motuen.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1002, "User existed.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1001, "User not existed.", HttpStatus.NOT_FOUND),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error.", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(1003, "User name must be at least 5 characters.", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters.", HttpStatus.BAD_REQUEST),
    INVALID_MESSAGE(1005, "Invalid message key.", HttpStatus.BAD_REQUEST), // If enter an error key during coding
    UNAUTHENTICATED(1006, "Unauthenticated.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission.", HttpStatus.FORBIDDEN);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private HttpStatusCode statusCode;
    private String message;
}
