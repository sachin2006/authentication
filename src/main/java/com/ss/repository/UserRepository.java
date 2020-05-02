package com.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.FINCRUser;

@Repository
public interface UserRepository extends JpaRepository<FINCRUser, Long> {
    public FINCRUser findByUserName(String userName);
    public FINCRUser findByEmail(String email);
}
