package com.csaba.blog.spring_blog.service;

import com.csaba.blog.spring_blog.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();
}
