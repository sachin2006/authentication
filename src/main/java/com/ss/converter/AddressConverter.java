/**
 * 
 */
package com.ss.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class  AddressConverter implements FINCRConverter<AddressData, Address> {

	Logger logger = LoggerFactory.getLogger(AddressConverter.class);
	
	@Autowired
	Populator<AddressData, Address> addressPopulator;
	
	@Override
	public Address convert(AddressData source) {
		Address addressModel = null;
		try {
			addressModel = (Address)createNewInstance(Address.class);
			addressPopulator.populate(source, addressModel);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			logger.error("Address: Data conversion error.");
			e.printStackTrace();
		}
		return addressModel;
	}
}
