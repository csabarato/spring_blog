package com.csaba.blog.spring_blog.controller;

import com.csaba.blog.spring_blog.model.BlogUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(BlogUser user, @RequestParam(name = "error", required = false) String error, Model model) {

        if (error != null) {
            model.addAttribute("error", "Login failed: Invalid credentials");
        }

        return "login";
    }
}
