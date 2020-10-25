package com.csaba.blog.spring_blog.repository;

import com.csaba.blog.spring_blog.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepostitory extends JpaRepository<BlogUser, Long> {

    BlogUser findByUsername(String username);

    BlogUser findByEmail(String email);
}
