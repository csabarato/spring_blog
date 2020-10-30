package com.csaba.blog.spring_blog.util;

import com.csaba.blog.spring_blog.model.Category;

import java.beans.PropertyEditorSupport;

public class CategoryPropEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Category c = new Category(text);
        this.setValue(c);
    }

    @Override
    public String getAsText() {
        Category c = (Category) this.getValue();
        return c.getName();
    }
}
