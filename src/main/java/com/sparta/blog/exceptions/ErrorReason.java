package com.sparta.blog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorReason {
    private String code;
    private String message;
}
