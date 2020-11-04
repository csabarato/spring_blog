package com.csaba.blog.spring_blog.repository;

import com.csaba.blog.spring_blog.model.BlogArticle;

import java.util.List;
import java.util.Optional;

public interface BlogArticleRepositoryCustom {

    List<BlogArticle> searchByParams(Optional<String> titleParam, Optional<String> authorParam);
}
