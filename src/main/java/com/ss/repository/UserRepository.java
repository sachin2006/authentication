package com.ss.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.FINCRUser;

@Repository
public interface UserRepository extends JpaRepository<FINCRUser, Long> {
	
    public FINCRUser findByUserName(String userName);
    
    public FINCRUser findByEmail(String email);
    
	public List<FINCRUser> findByActive(Boolean active);

	@Transactional
	@Modifying
	@Query("update FINCRUser set active = true where userName in :users")
	public int updateGrantAccess(@Param("users") List<String> users);
}
