package com.csaba.blog.spring_blog.converters;

import com.csaba.blog.spring_blog.dto.UserDto;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Set<String> map(Set<Role> roles);

    UserDto blogUserToUserDto(BlogUser blogUser);

    static String map(Role role) {
        return role != null ? role.getName() : null;
    }
}
