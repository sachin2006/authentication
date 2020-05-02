package com.ss.facade;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.ss.custom.exception.DuplicateEmailFoundExecption;
import com.ss.model.data.SignInRequestData;
import com.ss.model.data.SignUpRequestData;
import com.ss.model.data.UserData;

public interface UserFacade {

	public UserData createUser(SignUpRequestData userData) throws DuplicateEmailFoundExecption;

	public User athenticateUser(SignInRequestData signInData);
	
	public UserData generateAthenticationToken(User user);
}
