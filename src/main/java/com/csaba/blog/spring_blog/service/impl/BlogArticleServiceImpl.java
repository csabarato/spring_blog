package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.constants.BlogErrorType;
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
import java.util.Optional;

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
            Optional<BlogArticle> blogArticleToUpdateOpt = blogArticleRepository.findById(blogArticle.getId());

            if (blogArticleToUpdateOpt.isEmpty()) {
                throw new BlogException(BlogErrorType.EC_ARTICLE_NOT_FOUND);
            }

            if (!currentUser.isAdmin() && !currentUser.ownsArticle(blogArticleToUpdateOpt.get()) ) {
                throw new BlogException(BlogErrorType.EC_ARTICLE_EDITING_UNAUTHORIZED);
            }

            blogArticleToUpdateOpt.get().setTitle(blogArticle.getTitle());
            blogArticleToUpdateOpt.get().setText(blogArticle.getText());
            blogArticleToUpdateOpt.get().setCategories(blogArticle.getCategories());
            return blogArticleRepository.save(blogArticleToUpdateOpt.get());
        }

        blogArticle.setAuthor(currentUser);

        return blogArticleRepository.save(blogArticle);
    }

    @Override
    public BlogArticle findById(Long id) throws BlogException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BlogUser currentUser = (BlogUser) auth.getPrincipal();

        Optional<BlogArticle> blogArticleOpt = blogArticleRepository.findById(id);


        if (blogArticleOpt.isEmpty()) {
            throw new BlogException(BlogErrorType.EC_ARTICLE_NOT_FOUND);
        }

        if (!currentUser.isAdmin() && !currentUser.ownsArticle(blogArticleOpt.get())) {
            throw new BlogException(BlogErrorType.EC_ARTICLE_EDITING_UNAUTHORIZED);
        }

        return blogArticleOpt.get();
    }

    @Override
    public BlogArticle deleteById(Long id) throws BlogException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BlogUser currentUser = (BlogUser) auth.getPrincipal();

        Optional<BlogArticle> blogArticleOpt = blogArticleRepository.findById(id);

        if (blogArticleOpt.isEmpty()) {
            throw new BlogException(BlogErrorType.EC_ARTICLE_NOT_FOUND);
        }

        if (!currentUser.isAdmin() && !currentUser.ownsArticle(blogArticleOpt.get())) {
            throw new BlogException(BlogErrorType.EC_ARTICLE_EDITING_UNAUTHORIZED);
        }

        blogArticleRepository.deleteById(id);
        return blogArticleOpt.get();
    }
}
