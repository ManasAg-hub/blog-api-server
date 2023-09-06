package com.agarwal.blog.blogapi.services;

import com.agarwal.blog.blogapi.entity.Category;
import com.agarwal.blog.blogapi.entity.Post;
import com.agarwal.blog.blogapi.entity.User;
import com.agarwal.blog.blogapi.exceptions.ResourceNotFoundException;
import com.agarwal.blog.blogapi.payloads.PostDto;
import com.agarwal.blog.blogapi.payloads.PostResponse;
import com.agarwal.blog.blogapi.repositories.CategoryRepository;
import com.agarwal.blog.blogapi.repositories.PostRepository;
import com.agarwal.blog.blogapi.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Post post = convertToPost(postDto);

        post.setUser(user);
        post.setCategory(category);
        post.setCreatedDate(new Date());
        post.setImageName("default.png");

        Post savedPost = postRepository.save(post);

        return convertToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        postRepository.save(post);

        return convertToDto(post);
    }

    @Override
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return convertToDto(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> posts = postPage.getContent();
        return PostResponse.builder()
                .content(posts.stream().map(this::convertToDto).toList())
                .pageNumber(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements((int) postPage.getTotalElements())
                .totalPages(postPage.getTotalPages())
                .isLastPage(postPage.isLast())
                .build();
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Page<Post> postPage = postRepository.findByUser(user, pageable);
        List<Post> posts = postPage.getContent();
        return posts.stream().map(this::convertToDto).toList();

    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Page<Post> postPage = postRepository.findByCategory(category, pageable);
        List<Post> posts = postPage.getContent();
        return posts.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContaining(keyword);
        return posts.stream().map(this::convertToDto).toList();
    }

    private PostDto convertToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private Post convertToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
