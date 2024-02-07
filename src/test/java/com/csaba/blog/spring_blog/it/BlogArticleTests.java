package com.csaba.blog.spring_blog.it;

import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.Category;
import com.csaba.blog.spring_blog.repository.CategoryRepository;
import com.csaba.blog.spring_blog.service.BlogArticleService;
import com.csaba.blog.spring_blog.ut.utils.ArticleTestUtil;
import com.csaba.blog.spring_blog.ut.utils.UserTestUtil;
import com.csaba.blog.spring_blog.util.BlogException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration
class BlogArticleTests {

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String TEST_CATEGORY_1 = "testCat1";

    @BeforeAll
    void init() {

        Category testCat1 = new Category(TEST_CATEGORY_1);
        categoryRepository.save(testCat1);
    }

    @Test
    @WithUserDetails("admin")
    void testSaveAnArticle() throws BlogException {

        BlogArticle art = ArticleTestUtil.createArticle("it1",UserTestUtil.getAdmin());
        
        List<Category> categories = Collections.singletonList(categoryRepository.findByName(TEST_CATEGORY_1));

        art.setText("testText");
        art.setCategories(new HashSet<>(categories));

        BlogArticle blogArticle = blogArticleService.save(art, false);
        assertNotNull(blogArticle);
        assertEquals("it1", blogArticle.getTitle());
    }
}
