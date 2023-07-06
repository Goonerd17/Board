package com.sparta.blog.service;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.Post;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.PostRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        Post post = postRepository.save(new Post(postRequestDto, user));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public PostResponseDto getSinglePost(Long postID) {
        Post post = findPost(postID);
        return new PostResponseDto(post);
    }

    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = findPost(postId);
        if (user.getId() == (post.getUser().getId())) {
            post.update(postRequestDto);
            return new PostResponseDto(post);
        } else throw new IllegalArgumentException("해당 게시글 작성자만 수정할 수 있습니다");
    }

    public String deletePost(Long postID, User user) {
        Post post = findPost(postID);
        if (user.getId() == (post.getUser().getId())) {
            postRepository.delete(post);
            return "삭제 완료";
        } else throw new IllegalArgumentException("해당 게시글 작성자만 삭제할 수 있습니다");
    }

    @Transactional(readOnly = true)
    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));
    }

}

