package com.csaba.blog.spring_blog.controller;

import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.Comment;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import com.csaba.blog.spring_blog.service.CommentService;
import com.csaba.blog.spring_blog.util.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    BlogArticleService blogArticleService;

    @Autowired
    CommentService commentService;

    @PostMapping("/{articleId}")
    private String addComment(@PathVariable Long articleId, @Valid Comment comment, BindingResult bindingResult, Model model) throws BlogException {

        if (bindingResult.hasErrors()) {
            BlogArticle blogArticle = blogArticleService.findById(articleId);
            model.addAttribute("article", blogArticle);
            return "articles/article";
        }

        commentService.save(comment,articleId);

        return "redirect:/articles/get/"+ articleId;
    }

}
