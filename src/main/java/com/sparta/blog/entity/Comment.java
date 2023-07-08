package com.sparta.blog.entity;

import com.sparta.blog.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto commentRequestDto, User user) {
        this.content = commentRequestDto.getContent();
        this.username = user.getUsername();
        this.user = user;
    }

    private void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    protected void setPost(Post post) {
        this.post = post;
    }

    public Comment changeComment(CommentRequestDto commentRequestDto, User user) {
        if (user.getId() != this.getUser().getId() && user.getRole().getAuthority() == "ROLE_USER") throw new IllegalArgumentException("해당 댓글 작성자 혹은 관리자만 수정할 수 있습니다");
        this.update(commentRequestDto);
        return this;
    }

    public Comment checkDeleteableComment(User user) {
        if (user.getId() != this.getUser().getId() && user.getRole().getAuthority() == "ROLE_USER") throw new IllegalArgumentException("해당 댓글 작성자 혹은 관리자만 삭제할 수 있습니다");
        return this;
    }
}
