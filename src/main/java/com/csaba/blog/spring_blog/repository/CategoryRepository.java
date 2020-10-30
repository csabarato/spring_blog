package com.csaba.blog.spring_blog.repository;

import com.csaba.blog.spring_blog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByName(String name);
}
