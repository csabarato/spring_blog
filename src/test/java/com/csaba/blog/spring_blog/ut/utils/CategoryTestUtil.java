package com.csaba.blog.spring_blog.ut.utils;

import com.csaba.blog.spring_blog.model.Category;

public class CategoryTestUtil {

    public static Category createCategory(String name) {
        return new Category(name);
    }

}
