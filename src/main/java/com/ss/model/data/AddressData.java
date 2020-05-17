/**
 * 
 */
package com.ss.model.data;

/**
 * @author sachin
 *
 */
public class AddressData {

	public String addressLine1;
	public String addressLine2;
	public Long zipCode;
	private Long Id;
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public Long getZipCode() {
		return zipCode;
	}
	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}
	public void setId(Long id) {
		this.Id = id;
	}
	public Long getId() {
		return Id;
	}
	
	
}
