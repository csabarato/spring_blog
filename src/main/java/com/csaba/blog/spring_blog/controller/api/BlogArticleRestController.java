package com.csaba.blog.spring_blog.controller.api;

import com.csaba.blog.spring_blog.converters.BlogArticleConverter;
import com.csaba.blog.spring_blog.dto.BlogArticleDto;
import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import com.csaba.blog.spring_blog.util.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/articles", produces = "application/json")
public class BlogArticleRestController {

    @Autowired
    BlogArticleService blogArticleService;

    @Autowired
    BlogArticleConverter blogArticleConverter;

    @GetMapping("/list")
    public List<BlogArticleDto> listArticles() {
        return blogArticleConverter.convertToBlogArticleDtoList(blogArticleService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveArticle(@RequestBody BlogArticleDto blogArticleDto) throws BlogException {

        BlogArticle blogArticle = blogArticleConverter.convertToBlogArticle(blogArticleDto);
        blogArticleService.save(blogArticle, false);

        return ResponseEntity.ok("Article saved successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) throws BlogException {

        BlogArticle article  = blogArticleService.deleteById(id);

        if(article != null) {
            return ResponseEntity.ok("Article deleted with id: "+ article.getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found with id: "+ id);
    }
}
