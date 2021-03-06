package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.constants.BlogErrorType;
import com.csaba.blog.spring_blog.constants.Roles;
import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.repository.RoleRepository;
import com.csaba.blog.spring_blog.repository.UserRepostitory;
import com.csaba.blog.spring_blog.service.UserService;
import com.csaba.blog.spring_blog.util.AuthUtils;
import com.csaba.blog.spring_blog.util.BlogException;
import com.csaba.blog.spring_blog.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
        user.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName(Roles.ROLE_USER.name()))));

        return userRepostitory.save(user);
    }

    public List<BlogUser> findAll() {
        return userRepostitory.findAll();
    }

    @Override
    public BlogUser save(BlogUser user, MultipartFile file) throws IOException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByName(Roles.ROLE_USER.name()))));
        user.setProfilePic(ImageUtils.resizeImage(file));

        return userRepostitory.save(user);
    }

    @Override
    public void setUserEnabled(Long userId) throws BlogException {
        if (!AuthUtils.getCurrentUser().isAdmin()) {
            throw new BlogException(BlogErrorType.EC_BLOCK_USER_DISALLOWED);
        }

        Optional<BlogUser> userOpt = userRepostitory.findById(userId);
        if (userOpt.isEmpty()) {
            throw new BlogException(BlogErrorType.EC_USER_NOT_FOUND);
        }

        BlogUser user  = userOpt.get();

        user.setEnabled(!user.isEnabled());
        userRepostitory.save(user);
    }
}
