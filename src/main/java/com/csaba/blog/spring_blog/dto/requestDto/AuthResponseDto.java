package com.csaba.blog.spring_blog.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {

    private String username;
    private String jwtToken;

}
