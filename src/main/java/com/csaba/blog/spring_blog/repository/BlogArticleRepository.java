package com.csaba.blog.spring_blog.repository;

import com.csaba.blog.spring_blog.model.BlogArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogArticleRepository extends JpaRepository<BlogArticle, Long> {
}
