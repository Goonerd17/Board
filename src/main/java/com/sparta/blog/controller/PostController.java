package com.sparta.blog.controller;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.createPost(postRequestDto, userDetailsImpl.getUser());
    }

    @PatchMapping("/post/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.updatePost(postId, postRequestDto, userDetailsImpl.getUser());
    }

    @DeleteMapping ("/post/{postId}")
    public String deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.deletePost(postId, userDetailsImpl.getUser());
    }
}