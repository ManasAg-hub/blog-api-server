package com.agarwal.blog.blogapi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 10, message = "password should have atleast 3 and atmost 10 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "password should be alphanumeric")
    private String password;
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty
    @Size(min = 3, message = "Name should have atleast 3 characters")
    private String name;
    @NotEmpty
    private String about;
    private String token;
}
