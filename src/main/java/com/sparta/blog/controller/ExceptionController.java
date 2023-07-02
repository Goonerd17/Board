package com.sparta.blog.controller;

import com.sparta.blog.exceptions.ErrorReason;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorReason> illegalExHandle(IllegalArgumentException ie) {
        ErrorReason errorReason = new ErrorReason("fail", ie.getMessage());
        return new ResponseEntity(errorReason, HttpStatus.FORBIDDEN);
    }
}