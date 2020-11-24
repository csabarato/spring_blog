package com.csaba.blog.spring_blog.test.ut.utils;

import com.csaba.blog.spring_blog.constants.Roles;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.model.Role;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserTestUtil {

    public static BlogUser getAdmin() {

        BlogUser admin = new BlogUser();
        admin.setId(1L);
        admin.setUsername("test admin");
        admin.setEmail("admin@test.hu");

        Set<Role> roles = new HashSet<>(Collections.singletonList(new Role(Roles.ROLE_ADMIN.name())));
        admin.setRoles(roles);

        return admin;
    }

    public static BlogUser getUser() {

        BlogUser user = new BlogUser();
        user.setId(1L);
        user.setUsername("test user");
        user.setEmail("user@test.hu");

        Set<Role> roles = new HashSet<>(Collections.singletonList(new Role(Roles.ROLE_USER.name())));
        user.setRoles(roles);

        return user;
    }

}
