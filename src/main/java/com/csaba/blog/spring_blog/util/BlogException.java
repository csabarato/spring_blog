package com.csaba.blog.spring_blog.util;

import com.csaba.blog.spring_blog.constants.BlogErrorType;
import lombok.Getter;

@Getter
public class BlogException extends Exception {

    private final BlogErrorType errorType;

    public BlogException(BlogErrorType errorType) {
        this.errorType = errorType;
    }

}
