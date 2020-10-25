package com.csaba.blog.spring_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/profile")
    public String getUserProfile() {
        return "profile";
    }

}
