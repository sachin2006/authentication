/**
 * 
 */
package com.ss.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.model.Address;
import com.ss.model.data.AddressData;
import com.ss.populator.Populator;

/**
 * @author sachin
 *
 */
@Component
public class ReverseAddressConverter implements FINCRConverter<Address, AddressData> {

	@Autowired
	Populator<Address, AddressData> reverseAddressPopulator;
	
	@Override
	public AddressData convert(Address source) {	
		AddressData addressData = null;
		try {
			addressData = (AddressData)createNewInstance(AddressData.class);
			reverseAddressPopulator.populate(source,addressData);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return addressData;
	}
}
