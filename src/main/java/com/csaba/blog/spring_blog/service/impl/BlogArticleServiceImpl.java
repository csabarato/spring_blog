package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.repository.BlogArticleRepository;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogArticleServiceImpl implements BlogArticleService {

    private BlogArticleRepository blogArticleRepository;

    @Autowired
    public BlogArticleServiceImpl(BlogArticleRepository blogArticleRepository) {
        this.blogArticleRepository = blogArticleRepository;
    }

    @Override
    public List<BlogArticle> findAll() {
        return blogArticleRepository.findAll();
    }
}
