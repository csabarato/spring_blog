package com.csaba.blog.spring_blog.util;

import com.csaba.blog.spring_blog.model.BlogUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static BlogUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  (BlogUser) auth.getPrincipal();
    }
}
