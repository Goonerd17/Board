package com.sparta.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCodeEnum {

    TOKEN_INVALID(BAD_REQUEST, "400", "유효한 토큰이 아닙니다."),
    TOKEN_EXPIRED(BAD_REQUEST, "400", "토큰이 만료되었습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCodeEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

