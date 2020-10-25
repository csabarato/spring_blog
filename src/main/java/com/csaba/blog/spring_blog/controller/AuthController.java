package com.csaba.blog.spring_blog.controller;

import com.csaba.blog.spring_blog.model.BlogUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(BlogUser user) {
        return "login";
    }
}
