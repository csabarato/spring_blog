package com.csaba.blog.spring_blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {

    private String username;
    private String email;
    private String birthdate;
    private Set<String> roles;

}
