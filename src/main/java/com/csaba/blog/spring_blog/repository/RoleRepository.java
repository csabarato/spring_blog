package com.csaba.blog.spring_blog.repository;

import com.csaba.blog.spring_blog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String roleName);
}
