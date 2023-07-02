package com.sparta.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String author;
    private String description;
    private Long password;
}
