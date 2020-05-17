/**
 * 
 */
package com.ss.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.facade.AddressFacade;
import com.ss.model.data.AddressData;
import com.ss.model.data.FINCRResponseEntity;

/**
 * @author sachin
 *
 */
@RequestMapping(value = "/user")
@RestController
public class FINCRAddressController {

	@Autowired
	private AddressFacade addressFacade;
	
	Logger logger = LoggerFactory.getLogger(FINCRAddressController.class);
	
	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	public ResponseEntity<FINCRResponseEntity<List<AddressData>>> getAllAddress()
	{
		FINCRResponseEntity<List<AddressData>> FINCRRespEntity = new FINCRResponseEntity<List<AddressData>>();
		List<AddressData> addresses = addressFacade.getAllAddress();
		FINCRRespEntity.setData(addresses);
		return ResponseEntity.status(HttpStatus.OK).body(FINCRRespEntity);	
	}
	
	@RequestMapping(value = "/address/update", method = RequestMethod.POST)
	public ResponseEntity<FINCRResponseEntity<AddressData>> updateAddress(@RequestBody AddressData address)
	{
		FINCRResponseEntity<AddressData> FINCRRespEntity = new FINCRResponseEntity<AddressData>();
		try {
			AddressData addresses = addressFacade.updateAddress(address);
			FINCRRespEntity.setData(addresses);
		}
		catch(Exception e)
		{
			FINCRRespEntity.setErrors("Due to some techincal issue selected address is not updated.Please try after some time.");
			logger.error("Due to some techincal issue selected address is not updated.");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FINCRRespEntity);	
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(FINCRRespEntity);	
	}
	
	@RequestMapping(value = "/address/delete", method = RequestMethod.DELETE)
	public ResponseEntity<FINCRResponseEntity<Boolean>> deleteAddress(@RequestBody List<AddressData> addresses)
	{
		FINCRResponseEntity<Boolean> FINCRRespEntity = new FINCRResponseEntity<Boolean>();
		try {
			if(addressFacade.deleteAddress(addresses))
			{
				FINCRRespEntity.setMessage("User Addresses deleted successfully");
				FINCRRespEntity.setData(Boolean.TRUE);
			}
		}
		catch(Exception e)
		{
			FINCRRespEntity.setErrors("Due to some techincal issue selected address is not updated.Please try after some time.");
			FINCRRespEntity.setData(Boolean.FALSE);
			logger.error("Due to some techincal issue selected address is not updated.");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FINCRRespEntity);	
		}
		return ResponseEntity.status(HttpStatus.OK).body(FINCRRespEntity);	
	}
}
