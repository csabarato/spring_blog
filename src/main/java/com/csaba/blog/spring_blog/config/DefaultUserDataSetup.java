package com.csaba.blog.spring_blog.config;

import com.csaba.blog.spring_blog.constants.Roles;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.model.Role;
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

import java.util.Collections;

@Configuration
@Profile("dev")
@PropertySource("application-dev.properties")
@Slf4j
public class DefaultUserDataSetup implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepostitory userRepostitory;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;


    @Value("${default.username}")
    private String username;

    @Value("${default.email}")
    private String email;

    @Value("${default.password}")
    private String password;

    @Autowired
    public DefaultUserDataSetup(UserRepostitory userRepostitory,RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepostitory = userRepostitory;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isUserAlreadySetup = false;
    private boolean isRolesAlreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (isUserAlreadySetup) {
            return;
        }

        if (isRolesAlreadySetup) {
            return;
        }

        createRoles();

        BlogUser user = new BlogUser();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Role roleAdmin = roleRepository.findByName(Roles.ROLE_ADMIN.name());
        user.setRoles(Collections.singletonList(roleAdmin));

        userRepostitory.save(user);
        isUserAlreadySetup = true;
    }

    private void createRoles() {

        if (!isRolesAlreadySetup) {
            for (Roles role : Roles.values()) {
                roleRepository.save(new Role(role.toString()));
            }
            isRolesAlreadySetup = true;
        }
    }

}
