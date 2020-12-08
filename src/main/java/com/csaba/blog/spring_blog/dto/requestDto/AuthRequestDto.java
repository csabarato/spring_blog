package com.csaba.blog.spring_blog.dto.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthRequestDto {

    private String username;
    private String password;
}
