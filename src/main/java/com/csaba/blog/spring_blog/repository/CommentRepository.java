package com.csaba.blog.spring_blog.repository;

import com.csaba.blog.spring_blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
