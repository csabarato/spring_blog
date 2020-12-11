package com.csaba.blog.spring_blog.config;

import com.csaba.blog.spring_blog.constants.Roles;
import com.csaba.blog.spring_blog.model.*;
import com.csaba.blog.spring_blog.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Configuration
@Profile("dev")
@PropertySource("application-dev.properties")
@Slf4j
public class DefaultUserDataSetup implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepostitory userRepostitory;

    private RoleRepository roleRepository;

    private CategoryRepository categoryRepository;

    private CommentRepository commentRepository;

    private PasswordEncoder passwordEncoder;

    private BlogArticleRepository blogArticleRepository;

    private Comment h2Comment;

    private Comment test1Comment;

    @Value("${default.admin.username}")
    private String adminUsername;

    @Value("${default.admin.email}")
    private String adminEmail;

    @Value("${default.admin.password}")
    private String adminPassword;

    @Value("${default.user.username}")
    private String userUsername;

    @Value("${default.user.email}")
    private String userEmail;

    @Value("${default.user.password}")
    private String userPassword;

    private BlogArticle h2Article;
    private BlogArticle testArticle1;

    @Autowired
    public DefaultUserDataSetup(UserRepostitory userRepostitory,RoleRepository roleRepository,
                                CategoryRepository categoryRepository,
                                BlogArticleRepository blogArticleRepository,
                                PasswordEncoder passwordEncoder,
                                CommentRepository commentRepository) {
        this.userRepostitory = userRepostitory;
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.blogArticleRepository = blogArticleRepository;
        this.passwordEncoder = passwordEncoder;
        this.commentRepository = commentRepository;
    }

    private boolean isUserAlreadySetup = false;
    private boolean isRolesAlreadySetup = false;
    private boolean isCategoriesAlreadySetup = false;
    private boolean isArticlesAlreadySetup = false;
    private boolean isCommentsAlreadySetup = false;
    private boolean isArticleLikesAlreadySetup = false;

    private BlogUser admin = null;
    private BlogUser user = null;
    private List<Category> categories = null;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (!isRolesAlreadySetup) {
            createRoles();
        }

        if (!isUserAlreadySetup) {
            createUsers();
        }

        if (!isCategoriesAlreadySetup) {
            createCategories();
        }

        if(!isArticlesAlreadySetup) {
            createArticles();
        }

        if (!isCommentsAlreadySetup) {
            createComments();
        }

        if (!isArticleLikesAlreadySetup) {
            createArticleLikes();
        }
    }

    private void createUsers() {

        // Create admin
        admin = new BlogUser();

        admin.setUsername(adminUsername);
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));

        Role roleAdmin = roleRepository.findByName(Roles.ROLE_ADMIN.name());
        admin.setRoles(new HashSet<>(Collections.singletonList(roleAdmin)));
        admin.setEnabled(true);

        admin = userRepostitory.save(admin);

        // Create User
        user = new BlogUser();

        user.setUsername(userUsername);
        user.setEmail(userEmail);
        user.setPassword(passwordEncoder.encode(userPassword));
        user.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName(Roles.ROLE_USER.name()))));
        user.setEnabled(true);

        user = userRepostitory.save(user);

        isUserAlreadySetup = true;
    }

    private void createRoles() {

         for (Roles role : Roles.values()) {
                roleRepository.save(new Role(role.toString()));
         }
            isRolesAlreadySetup = true;
    }

    private void createCategories() {

         categories = Arrays.asList(
                        new Category("Sport"),
                        new Category("Lifestyle"),
                        new Category("Gastronomy"),
                        new Category("Music"));

        categories = categoryRepository.saveAll(categories);
        isCategoriesAlreadySetup = true;
    }

    private void createArticles() {

        Set<Category> article1Categories =
                new HashSet<>(Arrays.asList(categoryRepository.findByName("Sport"),
                        categoryRepository.findByName("Lifestyle")));

        h2Article = saveArticle(
                "H2 database connection", "Ez a cikk az embedded H2 db-ből van :)",
                admin, article1Categories);

        h2Article.setLikedBy(new HashSet<>());
        h2Article.getLikedBy().add(admin);

        Set<Category> article2Categories =
                new HashSet<>(Arrays.asList(categoryRepository.findByName("Gastronomy"),
                        categoryRepository.findByName("Lifestyle")));

        testArticle1 = saveArticle(
                "Test Article 1", "This is a test article",
                user, article2Categories);

        isArticlesAlreadySetup = true;
    }

    private BlogArticle saveArticle(String title, String text, BlogUser author, Set<Category> categories) {

        BlogArticle article = new BlogArticle();

        article.setTitle(title);
        article.setText(text);
        article.setAuthor(author);
        article.setCreatedAt(new Date());
        article.setCategories(categories);

        return blogArticleRepository.save(article);

    }

    private void createComments() {

        h2Comment = saveComment(h2Article, admin, "H2 is good for testing your application");
        test1Comment = saveComment(testArticle1, user, "Test comment 1");

        isCommentsAlreadySetup = true;
    }

    private Comment saveComment(BlogArticle blogArticle, BlogUser user, String text) {

        Comment comment = new Comment();
        comment.setBlogArticle(blogArticle);
        comment.setBlogUser(user);
        comment.setText(text);

        return commentRepository.save(comment);
    }

    private void createArticleLikes() {

        h2Article.setLikedBy(new HashSet<>(Arrays.asList(user)));

        testArticle1.setLikedBy(new HashSet<>(Arrays.asList(admin)));

        blogArticleRepository.save(h2Article);
        blogArticleRepository.save(testArticle1);

        isArticleLikesAlreadySetup = true;
    }

}
