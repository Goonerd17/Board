package com.sparta.blog.utils;

import com.sparta.blog.dto.ApiResponse;
import com.sparta.blog.dto.ErrorResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseUtils {

    public static <T>ApiResponse<T> ok(T response) {
        return new ApiResponse<>(true,response,null);
    }

    public static ApiResponse<?> error(String message, int status) {
        return new ApiResponse<>(false, null, new ErrorResponse(message, status));
    }
}
