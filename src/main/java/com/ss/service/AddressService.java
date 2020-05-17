/**
 * 
 */
package com.ss.service;

import java.util.List;

import com.ss.model.Address;
import com.ss.model.data.AddressData;

/**
 * @author sachin
 *
 */
public interface AddressService {

	public Address createNewAddress(AddressData address);
	
	public Address updateAddress(AddressData address);
	
	public Boolean deleteAddress(List<AddressData> addresses);

	public Address save(Address address);

	public List<AddressData> findAll();
	
}
