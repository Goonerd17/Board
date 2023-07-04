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
        ErrorReason errorReason = new ErrorReason(HttpStatus.BAD_REQUEST, "400", ie.getMessage());
        return ResponseEntity.status(errorReason.getStatus()).body(errorReason);
    }
}