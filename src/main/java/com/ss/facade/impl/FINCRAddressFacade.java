/**
 * 
 */
package com.ss.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ss.facade.AddressFacade;
import com.ss.model.Address;
import com.ss.model.data.AddressData;
import com.ss.service.AddressService;

/**
 * @author sachin
 *
 */
@Component
public class FINCRAddressFacade implements AddressFacade {

	@Autowired
	AddressService addressService;
	
	@Autowired
	public Converter<Address, AddressData> reverseAddressConverter;
	
	@Override
	public Address newAddress(AddressData address) {
		return addressService.createNewAddress(address);

	}

	@Override
	public AddressData updateAddress(AddressData address) {
		Address addressModel = addressService.updateAddress(address);
		return reverseAddressConverter.convert(addressModel);
	}

	@Override
	public Boolean deleteAddress(List<AddressData> addresses) {
		return addressService.deleteAddress(addresses);
	}

	@Override
	public List<AddressData> getAllAddress() {
		return addressService.findAll();
	}

}
