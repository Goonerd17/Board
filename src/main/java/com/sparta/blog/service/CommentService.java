package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = new Comment(commentRequestDto, user);
        postService.findPost(postId).addComment(comment);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = findComment(commentId).changeComment(commentRequestDto, user);
        return new CommentResponseDto(comment);
    }

    public String deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId).checkDeleteableComment(user);
        commentRepository.delete(comment);
        return "삭제완료";
    }

    @Transactional(readOnly = true)
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다"));
    }
}
