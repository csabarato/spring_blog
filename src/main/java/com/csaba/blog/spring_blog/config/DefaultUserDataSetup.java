package com.csaba.blog.spring_blog.config;

import com.csaba.blog.spring_blog.model.BlogUser;
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

@Configuration
@Profile("dev")
@PropertySource("application-dev.properties")
@Slf4j
public class DefaultUserDataSetup implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepostitory userRepostitory;

    private PasswordEncoder passwordEncoder;


    @Value("${default.username}")
    private String username;

    @Value("${default.email}")
    private String email;

    @Value("${default.password}")
    private String password;

    @Autowired
    public DefaultUserDataSetup(UserRepostitory userRepostitory, PasswordEncoder passwordEncoder) {
        this.userRepostitory = userRepostitory;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isUserAlreadySetup;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (isUserAlreadySetup) {
            return;
        }

        BlogUser user = new BlogUser();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepostitory.save(user);
        isUserAlreadySetup = true;
    }
}
