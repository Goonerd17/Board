package com.sparta.blog.exception;

import com.sparta.blog.dto.ApiResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.sparta.blog.utils.ResponseUtils.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@ResponseBody
public class BlogExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiResponse<?> handleIllegalArgsException(IllegalArgumentException ie) {
        return error(ie.getMessage(), BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiResponse<?> handleValidationErrors(MethodArgumentNotValidException me) {
        BindingResult bindingResult = me.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder validMessage = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            validMessage.append(fieldError.getDefaultMessage());
            validMessage.append(" ");
        }
        return error(String.valueOf(validMessage), BAD_REQUEST.value());
    }
}