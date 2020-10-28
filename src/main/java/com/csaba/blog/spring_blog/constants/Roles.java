package com.csaba.blog.spring_blog.constants;

public enum Roles {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_GUEST("ROLE_GUEST");

    String name;

    Roles(String name) {
        this.name = name;
    }
}
