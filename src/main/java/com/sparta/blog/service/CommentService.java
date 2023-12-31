package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.Post;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = new Comment(commentRequestDto, user);
        findPost(postId).addComment(comment);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = confirmComment(commentId,user);
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    public String deleteComment(Long commentId, User user) {
        Comment comment = confirmComment(commentId, user);
        commentRepository.delete(comment);
        return "삭제완료";
    }

    @Transactional(readOnly = true)
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다"));
    }

    @Transactional(readOnly = true)
    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));
    }

    private Comment confirmComment(Long commentId, User user) {
        Comment comment = findComment(commentId);
        if (!(user.getId() == comment.getUser().getId() || user.getRole().getAuthority() == "ROLE_ADMIN"))
            throw new IllegalArgumentException("해당 댓글 작성자 혹은 관리자만 수정,삭제할 수 있습니다");
        return comment;
    }
}
