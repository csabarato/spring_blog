package com.csaba.blog.spring_blog.service;

import com.csaba.blog.spring_blog.model.Comment;
import com.csaba.blog.spring_blog.util.BlogException;

public interface CommentService {

    Comment save(Comment comment, Long articleId) throws BlogException;
}
