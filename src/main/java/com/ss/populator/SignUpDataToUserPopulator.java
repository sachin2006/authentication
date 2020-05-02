/**
 * 
 */
package com.ss.populator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.model.FINCRUser;
import com.ss.model.Role;
import com.ss.model.data.SignUpRequestData;
import com.ss.repository.RoleRepository;

/**
 * @author sachin
 * Class to convert SignUp data to user entity
 */
@Component
public class SignUpDataToUserPopulator implements Populator<SignUpRequestData, FINCRUser> {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public FINCRUser populate(SignUpRequestData source, FINCRUser target) {
		Set<Role> roles = new HashSet<>();
		
		target.setUserName(source.getUserName());
		target.setEmail(source.getEmail());
		target.setMobile(source.getMobile());
		target.setActive(Boolean.FALSE);
		Role role = roleRepository.findByName("USER");
		roles.add(role);	
		target.setRoles(roles);
		
		return target;
	}
}
