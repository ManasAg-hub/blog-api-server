package com.agarwal.blog.blogapi.services;

import com.agarwal.blog.blogapi.entity.Post;
import com.agarwal.blog.blogapi.payloads.PostDto;
import com.agarwal.blog.blogapi.payloads.PostResponse;

import java.util.List;


public interface PostService {
    PostDto createPost(PostDto postDto, Long userId, Long categoryId);
    PostDto updatePost(PostDto postDto, Long id);
    PostDto getPost(Long id);
    void deletePost(Long id);
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<PostDto> getPostsByUser(Long userId, Integer pageNumber, Integer pageSize);
    List<PostDto> getPostsByCategory(Long categoryId, Integer pageNumber, Integer pageSize);

    List<PostDto> searchPosts(String keyword);
}
