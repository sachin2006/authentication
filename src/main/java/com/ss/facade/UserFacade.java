package com.ss.facade;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.ss.custom.exception.DuplicateEmailFoundExecption;
import com.ss.model.FINCRUser;
import com.ss.model.data.SignInRequestData;
import com.ss.model.data.SignUpRequestData;
import com.ss.model.data.UserData;

public interface UserFacade {

	public UserData createUser(SignUpRequestData userData) throws DuplicateEmailFoundExecption;

	public User athenticateUser(SignInRequestData signInData);
	
	public UserData generateAthenticationToken(User user);
	
	public void evitUserFromCache(String username);

	public List<UserData> getAllUserspendingForAccessGrant();

	public int updateGrantAccess(List<UserData> userList);
}
