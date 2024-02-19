package com.csaba.blog.spring_blog.converters;

import com.csaba.blog.spring_blog.dto.BlogArticleDto;
import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.Category;
import com.csaba.blog.spring_blog.service.CategoryService;
import com.csaba.blog.spring_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogArticleConverter {

    private final CategoryService categoryService;
    private final UserService userService;

    public BlogArticleConverter(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public BlogArticleDto convertToBlogArticleDto(BlogArticle blogArticle) {

        return new BlogArticleDto(
                blogArticle.getId(),
                blogArticle.getTitle(),
                blogArticle.getText(),
                blogArticle.getCategories().stream().map(Category::getName).collect(Collectors.toList()),
                blogArticle.getAuthor().getUsername());
    }

    public List<BlogArticleDto> convertToBlogArticleDtoList(List<BlogArticle> blogArticles) {

        return blogArticles.stream().map(this::convertToBlogArticleDto).collect(Collectors.toList());
    }

    public BlogArticle convertToBlogArticle(BlogArticleDto blogArticleDto) {

        BlogArticle blogArticle = new BlogArticle();

        if (blogArticleDto.getTitle() != null) {
            blogArticle.setTitle(blogArticleDto.getTitle());
        }

        if (blogArticleDto.getText() != null) {
            blogArticle.setText(blogArticleDto.getText());
        }

        if(blogArticleDto.getCategories() != null && !blogArticleDto.getCategories().isEmpty()) {

            blogArticle.setCategories(
                    blogArticleDto.getCategories().stream()
                        .map(categoryService::findByName)
                        .collect(Collectors.toSet()));
        }

        if(blogArticleDto.getAuthor() != null) {
            blogArticle.setAuthor(userService.findUserByUsername(blogArticleDto.getAuthor()));
        }

        return blogArticle;
    }
}
