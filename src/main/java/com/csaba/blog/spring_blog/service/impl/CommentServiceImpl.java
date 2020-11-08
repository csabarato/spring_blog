package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.model.Comment;
import com.csaba.blog.spring_blog.repository.CommentRepository;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import com.csaba.blog.spring_blog.service.CommentService;
import com.csaba.blog.spring_blog.util.AuthUtils;
import com.csaba.blog.spring_blog.util.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {


    private BlogArticleService blogArticleService;
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(BlogArticleService blogArticleService, CommentRepository commentRepository) {
        this.blogArticleService = blogArticleService;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment, Long articleId) throws BlogException {

        comment.setBlogArticle(blogArticleService.findById(articleId));
        comment.setBlogUser(AuthUtils.getCurrentUser());

        return commentRepository.save(comment);
    }
}
