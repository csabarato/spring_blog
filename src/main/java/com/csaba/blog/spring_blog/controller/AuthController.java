package com.csaba.blog.spring_blog.controller;

import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AuthController {

    private UserService userServiceImpl;

    @Autowired
    public AuthController(UserService userService) {
        this.userServiceImpl = userService;
    }

    @GetMapping("/login")
    public String login(BlogUser user, @RequestParam(name = "error", required = false) String error, Model model) {

        if (error != null) {
            model.addAttribute("error", "Login failed: Invalid credentials");
        }

        return "auth/login";
    }

    @GetMapping("/register")
    public String getRegisterForm(BlogUser user) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid BlogUser userToSave, BindingResult bindingResult, Model model) {

        UserDetails user = userServiceImpl.findUserByUsername(userToSave.getUsername());

        if (user != null) {
            model.addAttribute("error", "Error: User already exists with provided username");
            return "auth/register";
        }

        user = userServiceImpl.findUserByEmail(userToSave.getEmail());

        if (user != null) {
            model.addAttribute("error", "Error: User already exists with provided email address");
            return "auth/register";
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        userServiceImpl.save(userToSave);

        return "auth/login";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
