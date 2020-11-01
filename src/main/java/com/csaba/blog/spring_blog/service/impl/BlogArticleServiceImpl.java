package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.util.BlogException;
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
    public BlogArticle save(BlogArticle blogArticle, boolean isUpdate) throws BlogException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BlogUser currentUser = (BlogUser) auth.getPrincipal();

        if (isUpdate) {
            BlogArticle blogArticleToUpdate = blogArticleRepository.findById(blogArticle.getId()).orElse(null);

            if (blogArticleToUpdate == null) {
                throw new BlogException(1);
            }

            if (!currentUser.isAdmin() && !currentUser.ownsArticle(blogArticleToUpdate) ) {
                throw new BlogException(2);
            }

            blogArticleToUpdate.setTitle(blogArticle.getTitle());
            blogArticleToUpdate.setText(blogArticle.getText());
            blogArticleToUpdate.setCategories(blogArticle.getCategories());
            return blogArticleRepository.save(blogArticleToUpdate);
        }

        blogArticle.setAuthor(currentUser);

        return blogArticleRepository.save(blogArticle);
    }

    @Override
    public BlogArticle findById(Long id) throws BlogException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BlogUser currentUser = (BlogUser) auth.getPrincipal();

        BlogArticle blogArticle = blogArticleRepository.findById(id).orElse(null);


        if (blogArticle == null) {
            throw new BlogException(1);
        }

        if (!currentUser.isAdmin() && !currentUser.ownsArticle(blogArticle)) {
            throw new BlogException(2);
        }

        return blogArticle;
    }
}
