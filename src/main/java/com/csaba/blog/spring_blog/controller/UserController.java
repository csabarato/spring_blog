package com.csaba.blog.spring_blog.controller;

import com.csaba.blog.spring_blog.constants.BlogErrorType;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.service.UserService;
import com.csaba.blog.spring_blog.util.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile/{username}")
    public String getUserProfile(@PathVariable("username") String username, Model model) throws BlogException {

        BlogUser user = userService.findUserByUsername(username);

        if (user == null) {
            throw new BlogException(BlogErrorType.EC_USER_NOT_FOUND);
        }

        model.addAttribute("users", user);
        return "profile";
    }

    @GetMapping("/list")
    public String getUsers(Model model) {

        model.addAttribute("users",userService.findAll());
        return "profile";
    }
}
