package com.agarwal.blog.blogapi.controllers;

import com.agarwal.blog.blogapi.config.AppConstants;
import com.agarwal.blog.blogapi.payloads.ApiResponse;
import com.agarwal.blog.blogapi.payloads.CommentDto;
import com.agarwal.blog.blogapi.payloads.CommentResponse;
import com.agarwal.blog.blogapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/users/{userId}/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long userId, @PathVariable Long postId) {
        System.out.println("CommentDto: " + commentDto);
        CommentDto savedComment = commentService.createComment(commentDto, userId, postId);
        return ResponseEntity.ok(savedComment);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,@PathVariable(name = "commentId") Long id) {
        CommentDto updatedComment = commentService.updateComment(commentDto, id);
        return ResponseEntity.ok(updatedComment);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable(name = "commentId") Long id) {
        CommentDto commentDto = commentService.getComment(id);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable(name = "postId") Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable(name = "userId") Long userId) {
        List<CommentDto> comments = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments")
    public ResponseEntity<CommentResponse> getAllComments(@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = "content", required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortDir) {
        CommentResponse commentResponse = commentService.getAllComments(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "commentId") Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new ApiResponse(true, "Comment deleted successfully"), HttpStatus.OK);
    }
}
