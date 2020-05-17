package com.ss.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ss.model.Address;
import com.ss.model.FINCRUser;
import com.ss.model.data.AddressData;
import com.ss.repository.AddressRepository;
import com.ss.repository.UserRepository;
import com.ss.service.AddressService;

@Service
public class FINCRAddressService implements AddressService {

	Logger logger = LoggerFactory.getLogger(FINCRAddressService.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	public Converter<AddressData, Address> addressConverter;
	
	@Autowired
	public Converter<Address, AddressData> reverseAddressConverter;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public Address createNewAddress(AddressData address) {
		return addressConverter.convert(address);
	}

	@Override
	public Address updateAddress(AddressData address) {
		Address addressModel = addressConverter.convert(address);
		Optional<Address> checkAddress = addressRepository.findById(addressModel.getId());
		if(checkAddress.isPresent())
		{
			addressModel.setUser(checkAddress.get().getUser());
		}
		return this.save(addressModel);
	}

	@Override
	public Boolean deleteAddress(List<AddressData> addresses) {
		List<Address> addressList = addresses.parallelStream().map(address -> addressConverter.convert(address)).collect(Collectors.toList());
		addressRepository.deleteAll(addressList);
		return Boolean.TRUE;
	}

	@Override
	public Address save(Address address) {
		return addressRepository.save(address);
	}

	@Override
	public List<AddressData> findAll() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.debug("Getting all the address for user: " + userDetails.getUsername());
		FINCRUser user = userRepository.findByUserName(userDetails.getUsername());
		Set<Address> addresses = user.getAddresses();
		List<AddressData> addressList = addresses.parallelStream().map(address -> reverseAddressConverter.convert(address)).collect(Collectors.toList());
		return addressList;
	}

}
