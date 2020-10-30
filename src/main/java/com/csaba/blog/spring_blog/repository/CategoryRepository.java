package com.csaba.blog.spring_blog.repository;

import com.csaba.blog.spring_blog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
