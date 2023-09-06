package com.agarwal.blog.blogapi.payloads;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private Boolean success;
    private String message;
}
