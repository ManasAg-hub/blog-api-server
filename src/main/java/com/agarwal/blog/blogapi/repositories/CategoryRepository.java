package com.agarwal.blog.blogapi.repositories;

import com.agarwal.blog.blogapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category findByTitle(String title);
}
