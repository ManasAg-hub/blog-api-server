package com.agarwal.blog.blogapi.services;

import com.agarwal.blog.blogapi.entity.Category;
import com.agarwal.blog.blogapi.exceptions.ResourceAlreadyExistsException;
import com.agarwal.blog.blogapi.exceptions.ResourceNotFoundException;
import com.agarwal.blog.blogapi.payloads.CategoryDto;
import com.agarwal.blog.blogapi.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = convertToCategory(categoryDto);
        Category existingCategory = categoryRepository.findByTitle(category.getTitle());

        if(existingCategory != null) {
            throw new ResourceAlreadyExistsException("Category", "title", category.getTitle());
        }

        Category savedCategory = categoryRepository.save(category);
        return convertToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category = convertToCategory(categoryDto);
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        existingCategory.setTitle(category.getTitle());
        existingCategory.setDescription(category.getDescription());
        Category savedCategory = categoryRepository.save(existingCategory);
        return convertToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return convertToCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.delete(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id)));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToCategoryDto).toList();
    }

    private CategoryDto convertToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category convertToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
