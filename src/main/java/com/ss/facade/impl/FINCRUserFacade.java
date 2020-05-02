/**
 * 
 */
package com.ss.facade.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ss.custom.exception.DuplicateEmailFoundExecption;
import com.ss.facade.UserFacade;
import com.ss.model.FINCRUser;
import com.ss.model.data.SignInRequestData;
import com.ss.model.data.SignUpRequestData;
import com.ss.model.data.UserData;
import com.ss.service.impl.UserService;

/**
 * @author sachin
 *
 */
@Component
public class FINCRUserFacade implements UserFacade {

	@Autowired
	private UserService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserData createUser(SignUpRequestData signUpRequest) throws DuplicateEmailFoundExecption {
		FINCRUser user = userDetailsService.loadUserByEmail(signUpRequest.getEmail());
		UserData userData = null;
		if (Objects.isNull(user)) {
			user = userDetailsService.createUser(signUpRequest);
			userData = userDetailsService.save(user);
		} else {
			throw new DuplicateEmailFoundExecption("Email already taken.");
		}
		return userData;
	}

	@Override
	public User athenticateUser(SignInRequestData signInData) {
		Authentication authentication = null;
		User user = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInData.getUsername(), signInData.getPassword()));
			if(authentication.isAuthenticated())
			{
				return (User)authentication.getPrincipal();
			}
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("Incorrect username or password.", e);
		}
		return user;
	}
	
	public UserData generateAthenticationToken(User user)
	{
		return userDetailsService.generateAthenticationToken(user);
	}

}
