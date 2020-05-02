package com.ss.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ss.model.FINCRUser;
import com.ss.model.Role;
import com.ss.model.data.SignUpRequestData;
import com.ss.model.data.UserData;
import com.ss.populator.Populator;
import com.ss.repository.UserRepository;
import com.ss.util.FINCRJwtUtil;

@Service
public class FINCRUserDetailsService implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Converter<SignUpRequestData, FINCRUser> signUpDataToUserConverter;

	@Autowired
	Populator<FINCRUser, UserData> reverseUserTouserDataPopulator;

	@Override
	@Transactional
	@Cacheable(	value = "userCache",key = "#username")
	public UserDetails loadUserByUsername(String username) {
		UserDetails userDetails = null;
		FINCRUser user = userRepository.findByUserName(username);

		if (user == null)
			throw new UsernameNotFoundException(username);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		userDetails = new User(user.getUserName(), user.getPassword(), grantedAuthorities);
		return userDetails;
	}

	@Override
	public FINCRUser loadUserByEmail(String email) {
		FINCRUser user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public UserData save(FINCRUser user) {
		FINCRUser fincruser = userRepository.save(user);
		UserData userData = reverseUserTouserDataPopulator.populate(fincruser, new UserData());
		return userData;
	}

	@Override
	public FINCRUser createUser(SignUpRequestData signUpData) {
		return signUpDataToUserConverter.convert(signUpData);
	}

	@Override
	public UserData generateAthenticationToken(User principal) {
		final String token = FINCRJwtUtil.generateToken(principal.getUsername());
		UserData userData = new UserData(principal.getUsername(), principal.getAuthorities(), token);
		return userData;
	}
}
