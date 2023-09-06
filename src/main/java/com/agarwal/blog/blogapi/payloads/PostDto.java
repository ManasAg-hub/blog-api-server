package com.agarwal.blog.blogapi.payloads;

import com.agarwal.blog.blogapi.entity.Category;
import com.agarwal.blog.blogapi.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;

    //content should not be blank and should have atleast 5 characters
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 5, message = "Content should have atleast 5 characters")
    private String content;

    private String imageName;
    private Date createdDate;
    private UserDto user;
    private CategoryDto category;
}
