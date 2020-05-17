/**
 * 
 */
package com.ss.facade.impl;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ss.custom.exception.DuplicateEmailFoundExecption;
import com.ss.facade.UserFacade;
import com.ss.model.Address;
import com.ss.model.FINCRUser;
import com.ss.model.data.SignInRequestData;
import com.ss.model.data.SignUpRequestData;
import com.ss.model.data.UserData;
import com.ss.service.AddressService;
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
	private AddressService addressService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public UserData createUser(SignUpRequestData signUpRequest) throws DuplicateEmailFoundExecption {
		FINCRUser user = userDetailsService.loadUserByEmail(signUpRequest.getEmail());
		UserData userData = null;
		if (Objects.isNull(user)) {
			user = userDetailsService.createUser(signUpRequest);
			Address address = addressService.createNewAddress(signUpRequest.getAddressData());
			address.setUser(user);
			userData = userDetailsService.save(user);
			addressService.save(address);
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
					new UsernamePasswordAuthenticationToken(signInData.getUserName(), signInData.getPassword()));
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

	@Override
	public void evitUserFromCache(String username) {
		userDetailsService.evitUserFromCache(username);
		
	}

	@Override
	public List<UserData> getAllUserspendingForAccessGrant() {
		
		return userDetailsService.getAllUserspendingForAccessGrant();
	}

	@Override
	public int updateGrantAccess(List<UserData> userList) {
		return userDetailsService.updateGrantAccess(userList);
	}
}
