package com.csaba.blog.spring_blog.service;

import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.util.BlogException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {

    BlogUser findUserByUsername(String username);

    BlogUser findUserByEmail(String email);

    BlogUser save(BlogUser user);

    List<BlogUser> findAll();

    BlogUser save(BlogUser user, MultipartFile file) throws IOException;

    void setUserEnabled(Long userId) throws BlogException;
}
