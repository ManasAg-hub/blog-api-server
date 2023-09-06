package com.agarwal.blog.blogapi.services;

import com.agarwal.blog.blogapi.payloads.CommentDto;
import com.agarwal.blog.blogapi.payloads.CommentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long userId, Long postId);
    CommentDto updateComment(CommentDto commentDto, Long id);
    CommentDto getComment(Long id);
    void deleteComment(Long id);

    List<CommentDto> getCommentsByPost(Long postId);

    List<CommentDto> getCommentsByUser(Long userId);

    CommentResponse getAllComments(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
