package com.agarwal.blog.blogapi.services;

import com.agarwal.blog.blogapi.entity.Comment;
import com.agarwal.blog.blogapi.entity.Post;
import com.agarwal.blog.blogapi.entity.User;
import com.agarwal.blog.blogapi.exceptions.ResourceNotFoundException;
import com.agarwal.blog.blogapi.payloads.CommentDto;
import com.agarwal.blog.blogapi.payloads.CommentResponse;
import com.agarwal.blog.blogapi.repositories.CommentRepository;
import com.agarwal.blog.blogapi.repositories.PostRepository;
import com.agarwal.blog.blogapi.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = convertToComment(commentDto);
        System.out.println(comment);
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return convertToDto(savedComment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        comment.setContent(commentDto.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment);
    }

    @Override
    public CommentDto getComment(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        return convertToDto(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<CommentDto> getCommentsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Comment> comments = commentRepository.findByUser(user);

        return comments.stream().map(this::convertToDto).toList();
    }

    @Override
    public CommentResponse getAllComments(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Comment> comments = commentRepository.findAll(pageable);

        return CommentResponse.builder()
                .content(comments.stream().map(this::convertToDto).toList())
                .pageNumber(comments.getNumber())
                .pageSize(comments.getSize())
                .totalElements((int) comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .isLastPage(comments.isLast())
                .build();
    }
    private CommentDto convertToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment convertToComment(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
