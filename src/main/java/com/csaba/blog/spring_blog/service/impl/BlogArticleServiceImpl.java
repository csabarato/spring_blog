package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.repository.BlogArticleRepository;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    @Override
    public BlogArticle save(BlogArticle blogArticle, boolean isUpdate) {

        if (isUpdate) {
            BlogArticle blogArticleToUpdate = blogArticleRepository.findById(blogArticle.getId()).orElse(null);

            blogArticleToUpdate.setTitle(blogArticle.getTitle());
            blogArticleToUpdate.setText(blogArticle.getText());
            blogArticleToUpdate.setCategories(blogArticle.getCategories());
            return blogArticleRepository.save(blogArticleToUpdate);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        blogArticle.setAuthor((BlogUser) auth.getPrincipal());

        return blogArticleRepository.save(blogArticle);
    }

    @Override
    public BlogArticle findById(Long id) {
        return blogArticleRepository.findById(id).orElse(null);
    }
}
