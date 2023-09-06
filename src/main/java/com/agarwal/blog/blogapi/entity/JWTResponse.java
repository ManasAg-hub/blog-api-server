package com.agarwal.blog.blogapi.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTResponse {
    private String jwtToken;
    private String username;
}
