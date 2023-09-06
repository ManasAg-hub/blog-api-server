package com.agarwal.blog.blogapi.repositories;

import com.agarwal.blog.blogapi.entity.Comment;
import com.agarwal.blog.blogapi.entity.Post;
import com.agarwal.blog.blogapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByUser(User user);
}
