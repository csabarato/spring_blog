package com.csaba.blog.spring_blog.config;

import com.csaba.blog.spring_blog.constants.Roles;
import com.csaba.blog.spring_blog.model.BlogArticle;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.model.Category;
import com.csaba.blog.spring_blog.model.Role;
import com.csaba.blog.spring_blog.repository.BlogArticleRepository;
import com.csaba.blog.spring_blog.repository.CategoryRepository;
import com.csaba.blog.spring_blog.repository.RoleRepository;
import com.csaba.blog.spring_blog.repository.UserRepostitory;
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

    private PasswordEncoder passwordEncoder;

    private BlogArticleRepository blogArticleRepository;


    @Value("${default.username}")
    private String username;

    @Value("${default.email}")
    private String email;

    @Value("${default.password}")
    private String password;

    @Autowired
    public DefaultUserDataSetup(UserRepostitory userRepostitory,RoleRepository roleRepository,
                                CategoryRepository categoryRepository,
                                BlogArticleRepository blogArticleRepository,
                                PasswordEncoder passwordEncoder) {
        this.userRepostitory = userRepostitory;
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.blogArticleRepository = blogArticleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isUserAlreadySetup = false;
    private boolean isRolesAlreadySetup = false;
    private boolean isCategoriesAlreadySetup = false;
    private boolean isArticlesAlreadySetup = false;

    private BlogUser user1 = null;
    private BlogUser user2 = null;
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

    }

    private void createUsers() {

        user1 = new BlogUser();

        user1.setUsername(username);
        user1.setEmail(email);
        user1.setPassword(passwordEncoder.encode(password));

        Role roleAdmin = roleRepository.findByName(Roles.ROLE_ADMIN.name());
        user1.setRoles(Collections.singletonList(roleAdmin));

        user1 = userRepostitory.save(user1);

        user2 = new BlogUser();

        user2.setUsername("user");
        user2.setEmail("user@user.com");
        user2.setPassword(passwordEncoder.encode("asd"));
        user2.setRoles(Collections.singletonList(roleRepository.findByName(Roles.ROLE_USER.name())));

        user2 = userRepostitory.save(user2);

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

        BlogArticle article = new BlogArticle();

        article.setTitle("test 1");
        article.setText("test test asd");
        article.setAuthor(user1);

        article.setCategories(new HashSet<>(Arrays.asList(categories.get(0), categories.get(1))));

        blogArticleRepository.save(article);

        BlogArticle article2 = new BlogArticle();

        article2.setTitle("test 2");
        article2.setText("test test qwe");
        article2.setAuthor(user2);

        article2.setCategories(new HashSet<>(Arrays.asList(categories.get(2), categories.get(3))));

        blogArticleRepository.save(article2);

        isArticlesAlreadySetup = true;
    }
}
