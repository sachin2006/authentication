/**
 * 
 */
package com.ss.populator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.ss.model.FINCRUser;
import com.ss.model.data.UserData;

/**
 * @author sachin
 *
 */
@Component	
public class ReverseUserTouserDataPopulator implements Populator<FINCRUser, UserData> {

	@Override
	public UserData populate(FINCRUser source, UserData target) {
		target.setUserName(source.getUserName());
		target.setEmail(source.getEmail());
		target.setActive(source.getActive());
		Set<String> userRoles = new HashSet<>(); 
		source.getRoles().forEach(role ->{
			userRoles.add(role.getName());
		});
		target.setRoles(userRoles);
		return target;
	}

}
