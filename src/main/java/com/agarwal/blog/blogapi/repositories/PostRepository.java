package com.agarwal.blog.blogapi.repositories;

import com.agarwal.blog.blogapi.entity.Category;
import com.agarwal.blog.blogapi.entity.Post;
import com.agarwal.blog.blogapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
    List<Post> findByTitleContaining(String title);
}
