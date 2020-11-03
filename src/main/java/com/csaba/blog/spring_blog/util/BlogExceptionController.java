package com.csaba.blog.spring_blog.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class BlogExceptionController {

    private final int EC_ARTICLE_NOT_FOUND = 1;
    private final int EC_ARTICLE_EDITING_UNAUTHORIZED = 2;

    @ExceptionHandler(value = BlogException.class)
    public ModelAndView handleBlogException(BlogException blogEx) {

        ModelAndView model = new ModelAndView("error");
        model.setViewName("error");

        if (EC_ARTICLE_NOT_FOUND == blogEx.getErrorCode()) {
            model.addObject("error_msg", "Blog article not found with provided ID");
            model.addObject("code",HttpStatus.NOT_FOUND.value());
            model.addObject("code_message",HttpStatus.NOT_FOUND.getReasonPhrase());
        }

        if (EC_ARTICLE_EDITING_UNAUTHORIZED == blogEx.getErrorCode()) {
            model.addObject("error_msg", "User not allowed to edit this article");
            model.addObject("code",HttpStatus.UNAUTHORIZED.value());
            model.addObject("code_message",HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return model;
    }

}