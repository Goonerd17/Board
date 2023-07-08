package com.sparta.blog.service;

import com.sparta.blog.dto.PostList;
import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.entity.Post;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        Post post = postRepository.save(new Post(postRequestDto, user));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostList getPosts() {
        List<PostResponseDto> collect = postRepository.findAllByOrderByCreatedAtAtDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        return new PostList(collect);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getSinglePost(Long postID) {
        Post post = findPost(postID);
        return new PostResponseDto(post);
    }

    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = findPost(postId).checkChangeablePost(postRequestDto, user);
        return new PostResponseDto(post);
    }

    public String deletePost(Long postID, User user) {
        Post post = findPost(postID).checkDeleteablePost(user);
        postRepository.delete(post);
        return "삭제완료";
    }

    @Transactional(readOnly = true)
    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));
    }

}

