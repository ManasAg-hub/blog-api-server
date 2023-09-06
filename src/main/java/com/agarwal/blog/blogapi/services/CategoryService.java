package com.agarwal.blog.blogapi.services;

import com.agarwal.blog.blogapi.payloads.CategoryDto;

import java.util.List;
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Long id);
    CategoryDto getCategory(Long id);
    void deleteCategory(Long id);
    List<CategoryDto> getAllCategories();
}
