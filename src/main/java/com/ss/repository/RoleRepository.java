package com.ss.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    public Role findByName(String roleName);
}