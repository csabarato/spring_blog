package com.csaba.blog.spring_blog.service;

import com.csaba.blog.spring_blog.util.BlogException;
import com.csaba.blog.spring_blog.model.BlogArticle;

import java.util.List;

public interface BlogArticleService {

    List<BlogArticle> findAll();

    BlogArticle save(BlogArticle blogArticle, boolean isUpdate) throws BlogException;

    BlogArticle findById(Long id) throws BlogException;

    BlogArticle deleteById(Long id) throws BlogException;

    List<BlogArticle> searchByParams(String title, String author, String text, String dateFrom, String dateTo);
}
