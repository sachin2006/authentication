/**
 * 
 */
package com.ss.populator;

import org.springframework.stereotype.Component;

import com.ss.model.Address;
import com.ss.model.data.AddressData;

/**
 * @author sachin
 *
 */
@Component
public class ReverseAddressPopulator implements Populator<Address, AddressData> {

	@Override
	public AddressData populate(Address source, AddressData target) {
		target.setAddressLine1(source.getLine1());
		target.setAddressLine2(source.getLine2());
		target.setZipCode(source.getZipCode());
		target.setId(source.getId());
		return target;
	}
}
