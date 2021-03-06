/**
 * 
 */
package com.ss.model.data;

/**
 * @author sachin
 *
 */
public class SignUpRequestData {
	public String userName;
	public String password;
	public String email;
	public Long mobile;
	public AddressData addressData;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public AddressData getAddressData() {
		return addressData;
	}

	public void setAddressData(AddressData addressData) {
		this.addressData = addressData;
	}
	
}
