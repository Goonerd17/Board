package com.sparta.blog.controller;

import com.sparta.blog.dto.ApiResponse;
import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sparta.blog.utils.ResponseUtils.*;

@RestController
@RequestMapping("/api/post/{postId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<?> createComment(@PathVariable Long postId,
                                        @RequestBody CommentRequestDto commentRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ok(commentService.createComment(postId, commentRequestDto, userDetailsImpl.getUser()));
    }

    @PatchMapping("/{commentId}")
    public ApiResponse<?> modifyComment(@PathVariable Long commentId,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ok(commentService.updateComment(commentId, commentRequestDto, userDetailsImpl.getUser()));
    }

    @DeleteMapping ("/{commentId}")
    public ApiResponse<?> removeComment(@PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ok(commentService.deleteComment(commentId, userDetailsImpl.getUser()));
    }
}


