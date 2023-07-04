package com.sparta.blog.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    TOKEN_iNVALID(BAD_REQUEST, "400", "유효한 토큰이 아닙니다."),
    TOKEN_EXPIRED(UNAUTHORIZED, "401", "로그인이 필요합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

