package com.ss.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	Logger logger = LoggerFactory.getLogger(FINCRUserDetailsService.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Converter<SignUpRequestData, FINCRUser> signUpDataToUserConverter;

	@Autowired
	Populator<FINCRUser, UserData> reverseUserTouserDataPopulator;

	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	
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
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
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
	@Transactional
	public UserData save(FINCRUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

	@Override
	@CacheEvict(value = "userCache",key = "#username")
	public void evitUserFromCache(String username) {
		logger.debug(String.format("User with name %s is deleted from Cache",username));
	}

	@Override
	public List<UserData> getAllUserspendingForAccessGrant() {
		List<FINCRUser> fincrUserList = userRepository.findByActive(Boolean.FALSE);
		List<UserData> userDataList = new ArrayList<UserData>();
		if(CollectionUtils.isNotEmpty(fincrUserList))
		{
			fincrUserList.stream().parallel().forEach(fincrUser ->{
				UserData userData = reverseUserTouserDataPopulator.populate(fincrUser, new UserData());
				userDataList.add(userData);
			});
		}
		return userDataList;
	}

	@Override
	public int updateGrantAccess(List<UserData> userList) {
		List<String> users = userList.stream()
								.map(userData -> userData.getUserName())
								.collect(Collectors.toList());
		int count = userRepository.updateGrantAccess(users);
		
		return count;
	}
}
