package com.csaba.blog.spring_blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BlogArticleDto {

    private Long id;
    private String title;
    private String text;
    private List<String> categories;
    private String author;

}
