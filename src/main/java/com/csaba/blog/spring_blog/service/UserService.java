package com.csaba.blog.spring_blog.service;

import com.csaba.blog.spring_blog.model.BlogUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    BlogUser findUserByUsername(String username);

    BlogUser findUserByEmail(String email);

    BlogUser save(BlogUser user);

    List<BlogUser> findAll();
}
