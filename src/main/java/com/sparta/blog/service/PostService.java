package com.sparta.blog.service;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.entity.Post;
import com.sparta.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public String createPost(PostRequestDto requestPostDto) {
        Post post = new Post(requestPostDto);
        postRepository.save(post);
        return "등록 완료";
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findResponsePostDto(Long postID) {
        Post post = findPost(postID);
        return new PostResponseDto(post);
    }

    @Transactional
    public String updatePost(Long postID, PostRequestDto postRequestDto) {
        Post post = findPost(postID);
        if (postRequestDto.getPassword().equals(post.getPassword())) {
            post.update(postRequestDto);
            return "수정 완료";
        } else throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
    }

    @Transactional
    public String deletePost(Long postID, Long password) {
        Post post = findPost(postID);
        if (password.equals(post.getPassword())) {
            postRepository.delete(post);
            return "삭제 완료";
        } else throw new IllegalArgumentException("잘못된 비밀번호입니다");
    }

    @Transactional(readOnly = true)
    public Post findPost(Long postID) {
        return postRepository.findById(postID).orElseThrow(()->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));
    }
}

