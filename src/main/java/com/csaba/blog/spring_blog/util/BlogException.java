package com.csaba.blog.spring_blog.util;

import com.csaba.blog.spring_blog.constants.BlogErrorType;

public class BlogException extends Exception {

    private BlogErrorType errorType;

    public BlogException(BlogErrorType errorType) {
        this.errorType = errorType;
    }

    public BlogErrorType getErrorType() {
        return errorType;
    }
}
