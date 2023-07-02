package com.sparta.blog.entity;

import com.sparta.blog.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "password", nullable = false)
    private Long password;

    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.author = postRequestDto.getAuthor();
        this.description = postRequestDto.getDescription();
        this.password = postRequestDto.getPassword();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.description = postRequestDto.getDescription();
        this.password = postRequestDto.getPassword();
    }
}
