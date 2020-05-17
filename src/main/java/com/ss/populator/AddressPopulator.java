package com.ss.populator;

import org.springframework.stereotype.Component;

import com.ss.model.Address;
import com.ss.model.data.AddressData;

@Component
public class AddressPopulator implements Populator<AddressData,Address>  {

	@Override
	public Address populate(AddressData source, Address target) {
		target.setLine1(source.getAddressLine1());
		target.setLine2(source.getAddressLine2());
		target.setZipCode(source.getZipCode());
		target.setId(source.getId());
		return target;
	}

}
