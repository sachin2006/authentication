/**
 * 
 */
package com.ss.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ss.model.FINCRUser;
import com.ss.model.data.SignUpRequestData;
import com.ss.populator.Populator;

/**
 * @author sachin
 *
 */
@Component
public class SignUpDataToUserConverter implements Converter<SignUpRequestData, FINCRUser> {

	@Autowired
	Populator<SignUpRequestData, FINCRUser> singUpToUserPopulator;
	
	@Override
	public FINCRUser convert(SignUpRequestData source) {
		return (FINCRUser) singUpToUserPopulator.populate(source, createNewInstance());
	}
	
	public FINCRUser createNewInstance()
	{
		return new FINCRUser();
	}

}
