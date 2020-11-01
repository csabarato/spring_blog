package com.csaba.blog.spring_blog.util;

public class BlogException extends Exception {

    private Integer errorCode;

    public BlogException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
