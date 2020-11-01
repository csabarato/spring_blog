package com.csaba.blog.spring_blog.service;

import com.csaba.blog.spring_blog.model.BlogArticle;

import java.util.List;

public interface BlogArticleService {

    List<BlogArticle> findAll();

    BlogArticle save(BlogArticle blogArticle);

    BlogArticle findById(Long id);
}
