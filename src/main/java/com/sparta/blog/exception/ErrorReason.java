package com.sparta.blog.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
public class ErrorReason {

    public HttpStatus status;
    public String code;
    public String message;

    public ErrorReason(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ErrorReason(ErrorCodeEnum errorCodeEnum) {
        this.status = errorCodeEnum.getStatus();
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }
}
