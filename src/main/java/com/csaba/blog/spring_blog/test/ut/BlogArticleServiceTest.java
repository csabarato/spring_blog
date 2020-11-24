package com.csaba.blog.spring_blog.test.ut;


import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.repository.BlogArticleRepository;
import com.csaba.blog.spring_blog.service.impl.BlogArticleServiceImpl;
import com.csaba.blog.spring_blog.test.ut.utils.ArticleTestUtil;
import com.csaba.blog.spring_blog.test.ut.utils.UserTestUtil;
import com.csaba.blog.spring_blog.util.AuthUtils;
import com.csaba.blog.spring_blog.util.BlogException;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Log
public class BlogArticleServiceTest {

    @Mock
    private BlogArticleRepository blogArticleRepository;

    @InjectMocks
    private BlogArticleServiceImpl blogArticleServiceImpl;

    private static MockedStatic<AuthUtils> authUtils;

    @BeforeAll
    private static void init() {
        authUtils = Mockito.mockStatic(AuthUtils.class);
    }

    @Test
    void testIfFindAllIsSuccessful() {

        List<BlogArticle> testArticles = createTestArticles();

        when(blogArticleRepository.findAll(any(Sort.class))).thenReturn(testArticles);

        List<BlogArticle> blogArticles = blogArticleServiceImpl.findAll();

        assertNotNull(blogArticles);
        Assert.assertEquals(3, blogArticles.size());
        assertEquals(testArticles, blogArticles);
    }

    @Test
    void testSaveNewBlogArticle() {

        BlogUser admin = UserTestUtil.getAdmin();

        authUtils.when(AuthUtils::getCurrentUser).thenReturn(admin);
        BlogArticle art1 = ArticleTestUtil.createArticle("ASD");

        when(blogArticleRepository.save(any(BlogArticle.class))).thenReturn(art1);

        Assertions.assertDoesNotThrow(
                () -> {
                BlogArticle resultArt = blogArticleServiceImpl.save(art1, false);
                assertNotNull(resultArt.getAuthor());
                assertEquals(admin, resultArt.getAuthor());
                });
    }

    @Test
    void testUpdateArticle() throws BlogException{
        BlogUser admin = UserTestUtil.getAdmin();

        BlogArticle adminsArticle = ArticleTestUtil.createArticle("admins", admin);

        authUtils.when(AuthUtils::getCurrentUser).thenReturn(admin);

        when(blogArticleRepository.findById(any())).thenReturn(Optional.of(adminsArticle));

        BlogArticle updatedArticle = ArticleTestUtil.createArticle("updated");
        updatedArticle.setText("updated text");

        when(blogArticleRepository.save(any(BlogArticle.class))).thenReturn(adminsArticle);

        BlogArticle resultArticle = blogArticleServiceImpl.save(updatedArticle, true);

        assertEquals("updated", resultArticle.getTitle());
        assertEquals(admin, resultArticle.getAuthor());
    }

    @Test
    void testUpdateArticleWithUnauthorizedUser() {

        BlogUser user = UserTestUtil.getUser();
        BlogUser admin = UserTestUtil.getAdmin();

        BlogArticle adminsArticle = ArticleTestUtil.createArticle("admins", admin);

        authUtils.when(AuthUtils::getCurrentUser).thenReturn(user);

        when(blogArticleRepository.findById(any())).thenReturn(Optional.of(adminsArticle));

        assertThrows("User not allowed to edit this article",
                BlogException.class,
                () -> blogArticleServiceImpl.save(adminsArticle, true));
    }

    @Test
    void testUpdateNotExistingArticle() {
        BlogUser admin = UserTestUtil.getAdmin();

        BlogArticle adminsArticle = ArticleTestUtil.createArticle("admins", admin);

        authUtils.when(AuthUtils::getCurrentUser).thenReturn(admin);
        when(blogArticleRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows("Blog article not found with provided ID",
                BlogException.class,
                () -> blogArticleServiceImpl.save(adminsArticle, true));
    }

    private List<BlogArticle> createTestArticles() {

        Calendar cal = Calendar.getInstance();

        BlogArticle blogArticle1 = new BlogArticle();
        blogArticle1.setTitle("article1");
        blogArticle1.setText("art1");

        cal.set(2020,10,10);
        blogArticle1.setCreatedAt(cal.getTime());

        BlogArticle blogArticle2 = new BlogArticle();
        blogArticle2.setTitle("article2");
        blogArticle2.setText("art2");

        cal.set(2020,11,9);
        blogArticle2.setCreatedAt(cal.getTime());

        BlogArticle blogArticle3 = new BlogArticle();
        blogArticle3.setTitle("article3");
        blogArticle3.setText("art3");

        cal.set(2020,9,9);
        blogArticle3.setCreatedAt(cal.getTime());

        return Arrays.asList(blogArticle1, blogArticle2, blogArticle3);
    }
}
