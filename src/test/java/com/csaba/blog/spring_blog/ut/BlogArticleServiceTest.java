package com.csaba.blog.spring_blog.ut;


import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.repository.BlogArticleRepository;
import com.csaba.blog.spring_blog.service.impl.BlogArticleServiceImpl;
import com.csaba.blog.spring_blog.ut.utils.ArticleTestUtil;
import com.csaba.blog.spring_blog.ut.utils.UserTestUtil;
import com.csaba.blog.spring_blog.util.AuthUtils;
import com.csaba.blog.spring_blog.util.BlogException;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;



@ExtendWith(MockitoExtension.class)
@Log
public class BlogArticleServiceTest {

    @Mock
    private BlogArticleRepository blogArticleRepository;

    @InjectMocks
    private BlogArticleServiceImpl blogArticleServiceImpl;

    private static MockedStatic<AuthUtils> authUtils;

    @BeforeAll
    public static void init() {
        authUtils = Mockito.mockStatic(AuthUtils.class);
    }

    @Test
    void testIfFindAllIsSuccessful() {

        List<BlogArticle> testArticles = createTestArticles();

        Mockito.when(blogArticleRepository.findAll(ArgumentMatchers.any(Sort.class))).thenReturn(testArticles);

        List<BlogArticle> blogArticles = blogArticleServiceImpl.findAll();

        Assertions.assertNotNull(blogArticles);
        Assertions.assertEquals(3, blogArticles.size());
        Assertions.assertEquals(testArticles, blogArticles);
    }

    @Test
    void testSaveNewBlogArticle() {

        BlogUser admin = UserTestUtil.getAdmin();

        authUtils.when(AuthUtils::getCurrentUser).thenReturn(admin);
        BlogArticle art1 = ArticleTestUtil.createArticle("ASD");

        Mockito.when(blogArticleRepository.save(ArgumentMatchers.any(BlogArticle.class))).thenReturn(art1);

        Assertions.assertDoesNotThrow(
                () -> {
                BlogArticle resultArt = blogArticleServiceImpl.save(art1, false);
                Assertions.assertNotNull(resultArt.getAuthor());
                Assertions.assertEquals(admin, resultArt.getAuthor());
                });
    }

    @Test
    void testUpdateArticle() throws BlogException{
        BlogUser admin = UserTestUtil.getAdmin();

        BlogArticle adminsArticle = ArticleTestUtil.createArticle("admins", admin);

        authUtils.when(AuthUtils::getCurrentUser).thenReturn(admin);

        Mockito.when(blogArticleRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(adminsArticle));

        BlogArticle updatedArticle = ArticleTestUtil.createArticle("updated");
        updatedArticle.setText("updated text");

        Mockito.when(blogArticleRepository.save(ArgumentMatchers.any(BlogArticle.class))).thenReturn(adminsArticle);

        BlogArticle resultArticle = blogArticleServiceImpl.save(updatedArticle, true);

        Assertions.assertEquals("updated", resultArticle.getTitle());
        Assertions.assertEquals(admin, resultArticle.getAuthor());
    }

    @Test
    void testUpdateArticleWithUnauthorizedUser() {

        BlogUser user = UserTestUtil.getUser();
        BlogUser admin = UserTestUtil.getAdmin();

        BlogArticle adminsArticle = ArticleTestUtil.createArticle("admins", admin);

        authUtils.when(AuthUtils::getCurrentUser).thenReturn(user);

        Mockito.when(blogArticleRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(adminsArticle));

        Assertions.assertThrows(
                BlogException.class, () -> blogArticleServiceImpl.save(adminsArticle, true),
                "User not allowed to edit this article");
    }

    @Test
    void testUpdateNotExistingArticle() {
        BlogUser admin = UserTestUtil.getAdmin();

        BlogArticle adminsArticle = ArticleTestUtil.createArticle("admins", admin);

        authUtils.when(AuthUtils::getCurrentUser).thenReturn(admin);
        Mockito.when(blogArticleRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                BlogException.class,
                () -> blogArticleServiceImpl.save(adminsArticle, true),
                "Blog article not found with provided ID");
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
