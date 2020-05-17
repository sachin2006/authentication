/**
 * 
 */
package com.ss.facade;

import java.util.List;

import com.ss.model.Address;
import com.ss.model.data.AddressData;

/**
 * @author sachin
 *
 */
public interface AddressFacade {

	public Address newAddress(AddressData address);
	
	public AddressData updateAddress(AddressData address);
	
	public Boolean deleteAddress(List<AddressData> addresses);

	public List<AddressData> getAllAddress();
}
