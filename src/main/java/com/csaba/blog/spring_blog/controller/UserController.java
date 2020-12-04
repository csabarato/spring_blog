package com.csaba.blog.spring_blog.controller;

import com.csaba.blog.spring_blog.constants.BlogErrorType;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.service.UserService;
import com.csaba.blog.spring_blog.util.AuthUtils;
import com.csaba.blog.spring_blog.util.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile/{username}")
    public String getUserProfile(@PathVariable("username") String username , Model model) throws BlogException {

        BlogUser user = userService.findUserByUsername(username);

        if (user == null) {
            throw new BlogException(BlogErrorType.EC_USER_NOT_FOUND);
        }

        model.addAttribute("users", user);

        if (user.getProfilePic() != null) {
            String image = Base64.getEncoder().encodeToString(user.getProfilePic());
            model.addAttribute("image", image);
        }

        return "profile";
    }

    @GetMapping("/list")
    public String getUsers(Model model) {

        model.addAttribute("users",userService.findAll());

        BlogUser user = AuthUtils.getCurrentUser();
        if (user.getProfilePic() != null) {
            String image = Base64.getEncoder().encodeToString(user.getProfilePic());
            model.addAttribute("image", image);
        }

        return "profile";
    }

    @PostMapping("/updateProfilePic")
    public String updateProfilePicture(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        BlogUser currentUser = AuthUtils.getCurrentUser();

        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                model.addAttribute("users", currentUser);
                model.addAttribute("profilePicError", "You have to choose a picutre!");

                if (currentUser.getProfilePic() != null) {
                    String image = Base64.getEncoder().encodeToString(currentUser.getProfilePic());
                    model.addAttribute("image", image);
                }
                return "profile";
        }

        userService.save(currentUser, file);
        return "redirect:/user/profile/"+ currentUser.getUsername();
    }

    @GetMapping("/block/{id}")
    public String blockUser(@PathVariable Long id) throws BlogException {
        userService.setUserEnabled(id);
        return "redirect:/user/list";
    }
}
