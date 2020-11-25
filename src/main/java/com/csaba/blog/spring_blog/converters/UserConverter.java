package com.csaba.blog.spring_blog.converters;

import com.csaba.blog.spring_blog.dto.UserDto;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.model.Role;
import com.csaba.blog.spring_blog.util.DateConverter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDto convertToUserDto(BlogUser blogUser) {

        UserDto userDto = new UserDto();

        userDto.setUsername(blogUser.getUsername());
        userDto.setEmail(blogUser.getEmail());

        if (blogUser.getBirthdate() != null) {
            userDto.setBirthdate(DateConverter.convertDateToString(blogUser.getBirthdate()));
        }

        userDto.setRoles(blogUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return userDto;
    }
}
