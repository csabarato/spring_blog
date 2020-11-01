package com.csaba.blog.spring_blog.controller;

import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.Category;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import com.csaba.blog.spring_blog.service.CategoryService;
import com.csaba.blog.spring_blog.util.CategoryPropEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/articles")
public class BlogArticleController {

    @Autowired
    BlogArticleService blogArticleService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/list")
    public String listArticlesPost(Model model) {

        model.addAttribute("articles", blogArticleService.findAll());
        return "articles/blog_articles";
    }


    @GetMapping("/list")
    public String listArticles(Model model) {

        model.addAttribute("articles", blogArticleService.findAll());
        return "articles/blog_articles";
    }

    @GetMapping("/new")
    public String getCreateArticleForm(BlogArticle blogArticle, Model model) {

        model.addAttribute("categories", categoryService.findAll());
        return "articles/create_article";
    }

    @PostMapping("/new")
    public String saveArticle(@Valid BlogArticle blogArticle, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "articles/create_article";
        }

        blogArticleService.save(blogArticle, false);

        return "redirect:list";
    }

    @GetMapping("/edit/{id}")
    public String getEditArticleForm(@PathVariable long id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("blogArticle",  blogArticleService.findById(id));
        return "articles/create_article";
    }

    @PostMapping("/edit/{id}")
    public String editArticleForm(@PathVariable Long id,@Valid BlogArticle blogArticle, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "articles/create_article";
        }

        blogArticleService.save(blogArticle, true);
        return "redirect:/articles/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Category.class, new CategoryPropEditor());
    }
}
