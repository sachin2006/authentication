/**
 * 
 */
package com.ss.model.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author sachin
 *
 */
public class UserData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7807993505829058636L;
	
	public String userName;
	public String email;
	public Boolean active;
	public Long mobile;
	
	public Set<String> roles;
	public String token;
	
	public UserData() {
	}
	
	public UserData(String username, Collection<GrantedAuthority> authorities, String token) {
		this.userName = username;
		this.email = username;
		this.roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	
}
