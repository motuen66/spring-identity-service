package com.motuen.identity_service.dto.request;

public enum ErrorCode {
    USER_EXISTED(1002, "User existed."),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error."),
    USERNAME_INVALID(1003, "User name must be at least 5 characters."),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters."),
    INVALID_MESSAGE(1005, "Invalid message key.") // If enter an error key during coding
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private String message;
}
