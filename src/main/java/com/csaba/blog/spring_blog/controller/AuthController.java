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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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

        return "auth/login";
    }

    @GetMapping("/login/error")
    public String loginError(BlogUser blogUser, Model model) {

        model.addAttribute("error", "Login failed");
        return "auth/login";
    }

    @GetMapping("/register")
    public String getRegisterForm(BlogUser user) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("file") MultipartFile file,
            @Valid BlogUser userToSave, BindingResult bindingResult, Model model) throws IOException {

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

        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            userServiceImpl.save(userToSave);
        } else {
            userServiceImpl.save(userToSave, file);
        }

        return "auth/login";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
