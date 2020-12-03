package com.csaba.blog.spring_blog.constants;

public enum BlogErrorType {

    EC_ARTICLE_NOT_FOUND (1),
    EC_ARTICLE_EDITING_UNAUTHORIZED(2),
    EC_COMMENT_NOT_FOUND(3),
    EC_USER_NOT_FOUND(4),
    EC_BLOCK_USER_DISALLOWED(5),
    EC_USER_IS_BLOCKED(6);

    public int errorCode;

    BlogErrorType(int errorCode) {
        this.errorCode = errorCode;
    }
}
