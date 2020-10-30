package com.csaba.blog.spring_blog.controller;

import com.csaba.blog.spring_blog.service.BlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class BlogArticleController {

    @Autowired
    BlogArticleService blogArticleService;

    @PostMapping("/list")
    public String listArticlesPost(Model model) {

        model.addAttribute("articles", blogArticleService.findAll());
        return "blog_articles";
    }


    @GetMapping("/list")
    public String listArticles(Model model) {

        model.addAttribute("articles", blogArticleService.findAll());
        return "blog_articles";
    }
}
