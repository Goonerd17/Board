package com.sparta.blog.repository;

import com.sparta.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p left join fetch p.commentList cl order by p.createdAt desc, cl.createdAt desc")
    List<Post> findAllByOrderByCreatedAtAtDesc();

}
