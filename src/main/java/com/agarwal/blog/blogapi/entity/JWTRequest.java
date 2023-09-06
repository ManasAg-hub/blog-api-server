package com.agarwal.blog.blogapi.entity;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTRequest {
    private String password;
    private String email;

}
