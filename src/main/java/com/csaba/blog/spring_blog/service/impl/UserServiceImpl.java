package com.csaba.blog.spring_blog.service.impl;

import com.csaba.blog.spring_blog.model.BlogUser;
import com.csaba.blog.spring_blog.repository.UserRepostitory;
import com.csaba.blog.spring_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepostitory userRepostitory;

    @Autowired
    public UserServiceImpl(UserRepostitory userRepostitory) {
        this.userRepostitory = userRepostitory;
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
}
