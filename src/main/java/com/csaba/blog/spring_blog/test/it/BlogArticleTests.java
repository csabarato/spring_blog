package com.csaba.blog.spring_blog.test.it;

import com.csaba.blog.spring_blog.constants.Roles;
import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.Category;
import com.csaba.blog.spring_blog.repository.BlogArticleRepository;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import com.csaba.blog.spring_blog.test.ut.utils.ArticleTestUtil;
import com.csaba.blog.spring_blog.test.ut.utils.CategoryTestUtil;
import com.csaba.blog.spring_blog.test.ut.utils.UserTestUtil;
import com.csaba.blog.spring_blog.util.BlogException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BlogArticleTests {

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private BlogArticleRepository blogArticleRepository;

    @Test
    @WithUserDetails
    public void testSaveAnArticle() throws BlogException {

        BlogArticle art = ArticleTestUtil.createArticle("it1",UserTestUtil.getAdmin());

        List<Category> categories = Arrays.asList(CategoryTestUtil.createCategory("testCat1"));
        art.setText("testText");
        art.setCategories(new HashSet<>(categories));

        blogArticleService.save(art, false);
    }
}
