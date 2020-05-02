/**
 * 
 */
package com.ss.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ss.model.FINCRUser;
import com.ss.model.data.SignUpRequestData;
import com.ss.model.data.UserData;

/**
 * @author sachin
 * Class to service user services
 */
public interface UserService extends UserDetailsService {
	 public FINCRUser loadUserByEmail(String email);
	 public UserData save(FINCRUser user);
	 /**
	  * Function to create new user based on sign p data.
	  * @param signUpData
	  * @return {@link FINCRUser}
	  * */
	 public FINCRUser createUser(SignUpRequestData signUpData);
	
	 public UserData generateAthenticationToken(User principal);
}
