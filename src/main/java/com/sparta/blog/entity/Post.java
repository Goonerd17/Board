package com.sparta.blog.entity;

import com.sparta.blog.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setPost(this);
    }

    public Post(PostRequestDto postRequestDto, User user) {
        this.title = postRequestDto.getTitle();
        this.username = user.getUsername();
        this.description = postRequestDto.getDescription();
        this.user = user;
    }

    private void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.description = postRequestDto.getDescription();
    }

    public Post changePost(PostRequestDto postRequestDto, User user) {
        if (user.getId() != this.getUser().getId() && user.getRole().getAuthority() == "ROLE_USER") throw new IllegalArgumentException("해당 게시글 작성자 혹은 관리자만 수정할 수 있습니다");
        this.update(postRequestDto);
        return this;
    }

    public Post checkDeleteablePost(User user) {
        if (user.getId() != this.getUser().getId() && user.getRole().getAuthority() == "ROLE_USER") throw new IllegalArgumentException("해당 게시글 작성자 혹은 관리자만 삭제할 수 있습니다");
        return this;
    }
}
