package com.sparta.blog.controller;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;

    @GetMapping("/posts")
    public List<PostResponseDto> readAllPosts() {return postService.getPosts();}


    @GetMapping("/post/{postId}")
    public PostResponseDto readOnePost(@PathVariable Long postId) {
        return postService.findResponsePostDto(postId);
    }


    @PostMapping("/post")
    public String createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }


    @PatchMapping("/post/{postId}")
    public String updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(postId, postRequestDto);
    }

    @DeleteMapping ("/post/{postId}")
    public String deletePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postService.deletePost(postId, postRequestDto.getPassword());
    }
}