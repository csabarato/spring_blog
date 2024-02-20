package com.csaba.blog.spring_blog.controller.api;

import com.csaba.blog.spring_blog.converters.UserMapper;
import com.csaba.blog.spring_blog.dto.UserDto;
import com.csaba.blog.spring_blog.util.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserRestController {

    @GetMapping("/profile")
    public UserDto getUserProfile() {
        return UserMapper.INSTANCE.blogUserToUserDto(AuthUtils.getCurrentUser());
    }
}
