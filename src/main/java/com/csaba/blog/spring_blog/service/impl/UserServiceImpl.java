package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.constants.Roles;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.repository.RoleRepository;
import com.csaba.blog.spring_blog.repository.UserRepostitory;
import com.csaba.blog.spring_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepostitory userRepostitory;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepostitory userRepostitory, RoleRepository roleRepository) {
        this.userRepostitory = userRepostitory;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String unameOrEmail) throws UsernameNotFoundException {


        Optional<BlogUser> userOpt = Optional.ofNullable(userRepostitory.findByUsername(unameOrEmail));

        if (userOpt.isEmpty()) {
            userOpt = Optional.ofNullable(userRepostitory.findByEmail(unameOrEmail));
        }

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with the provided username or email: "+ unameOrEmail);
        }

        return userOpt.get();
    }

    @Override
    public BlogUser findUserByUsername(String username) {
        return userRepostitory.findByUsername(username);
    }

    @Override
    public BlogUser findUserByEmail(String email) {
        return userRepostitory.findByEmail(email);
    }

    @Override
    public BlogUser save(BlogUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName(Roles.ROLE_USER.name())));

        return userRepostitory.save(user);
    }
}
