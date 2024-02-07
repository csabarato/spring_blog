package com.csaba.blog.spring_blog.ut.utils;

import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.BlogUser;

import java.util.Calendar;
import java.util.Date;

public class ArticleTestUtil {

    public static BlogArticle createArticle(String title, BlogUser author, int creationYear, int creationMonth, int creationDay) {

        Calendar cal = Calendar.getInstance();

        BlogArticle article = new BlogArticle();
        article.setTitle(title);
        cal.set(creationYear,creationMonth,creationDay);
        article.setCreatedAt(cal.getTime());
        article.setAuthor(author);

        return article;
    }

    public static BlogArticle createArticle(String title) {

        BlogArticle article = new BlogArticle();
        article.setTitle(title);
        article.setCreatedAt(new Date());

        return article;
    }

    public static BlogArticle createArticle(String title, BlogUser author) {

        BlogArticle article = new BlogArticle();
        article.setTitle(title);
        article.setCreatedAt(new Date());
        article.setAuthor(author);

        return article;
    }

}
