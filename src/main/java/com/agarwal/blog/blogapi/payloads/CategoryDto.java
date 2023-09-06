package com.agarwal.blog.blogapi.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long Id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String title;
    @NotBlank
    @Size(min = 3, max = 100)
    private String description;
}
