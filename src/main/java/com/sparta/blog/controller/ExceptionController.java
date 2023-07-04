package com.sparta.blog.controller;

import com.sparta.blog.exceptions.ErrorReason;
import com.sparta.blog.exceptions.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorReason> illegalExHandle(IllegalArgumentException ie) {
        ErrorReason errorReason = new ErrorReason("fail", ie.getMessage());
        return new ResponseEntity<>(errorReason, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorReason> usernameNotFoundExHandle(UsernameNotFoundException ue) {
        ErrorReason errorReason = new ErrorReason("fail", ue.getMessage());
        return new ResponseEntity<>(errorReason, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorReason> invalidTokenExHandle(InvalidTokenException ite) {
        ErrorReason errorReason = new ErrorReason("fail", ite.getMessage());
        return new ResponseEntity<>(errorReason, HttpStatus.BAD_REQUEST);
    }
}