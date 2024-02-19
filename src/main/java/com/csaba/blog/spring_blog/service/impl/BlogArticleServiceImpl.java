package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.constants.BlogErrorType;
import com.csaba.blog.spring_blog.util.AuthUtils;
import com.csaba.blog.spring_blog.util.BlogException;
import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.repository.BlogArticleRepository;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import com.csaba.blog.spring_blog.util.DateConverter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Transactional
public class BlogArticleServiceImpl implements BlogArticleService {

    private final BlogArticleRepository blogArticleRepository;

    public BlogArticleServiceImpl(BlogArticleRepository blogArticleRepository) {
        this.blogArticleRepository = blogArticleRepository;
    }

    @Override
    public List<BlogArticle> findAll() {
        return blogArticleRepository.findAll(Sort.by("createdAt").descending());
    }

    @Override
    public BlogArticle save(BlogArticle blogArticle, boolean isUpdate) throws BlogException {

        BlogUser currentUser = AuthUtils.getCurrentUser();

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

        Optional<BlogArticle> blogArticleOpt = blogArticleRepository.findById(id);

        if (blogArticleOpt.isEmpty()) {
            throw new BlogException(BlogErrorType.EC_ARTICLE_NOT_FOUND);
        }

        return blogArticleOpt.get();
    }

    @Override
    public BlogArticle deleteById(Long id) throws BlogException {

        BlogUser currentUser = AuthUtils.getCurrentUser();

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

    @Override
    public List<BlogArticle> searchByParams(String title, String author, String text, String dateFrom, String dateTo) {

        Optional<String> titleOpt = Optional.ofNullable(title).filter(Predicate.not(String::isEmpty));
        Optional<String> authorOpt = Optional.ofNullable(author).filter(Predicate.not(String::isEmpty));
        Optional<String> textOpt = Optional.ofNullable(text).filter(Predicate.not(String::isEmpty));

        Optional<Date> dateFromOpt = Optional.ofNullable(DateConverter.convertStringToDate(dateFrom));
        Optional<Date> dateToOpt = Optional.ofNullable(DateConverter.convertStringToDate(dateTo));

        return blogArticleRepository.searchByParams(titleOpt, authorOpt, textOpt, dateFromOpt, dateToOpt);
    }

    @Override
    public void addOrRemoveArticleLike(Long id) throws BlogException {

        BlogUser currentUser = AuthUtils.getCurrentUser();
        BlogArticle blogArticle = this.findById(id);


        if (blogArticle.getLikedBy().contains(currentUser)) {
            blogArticle.getLikedBy().remove(currentUser);
        } else {
            blogArticle.getLikedBy().add(currentUser);
        }
        blogArticleRepository.save(blogArticle);
    }
}
