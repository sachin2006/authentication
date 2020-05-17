/**
 * 
 */
package com.ss.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.custom.exception.DuplicateEmailFoundExecption;
import com.ss.facade.UserFacade;
import com.ss.model.data.FINCRResponseEntity;
import com.ss.model.data.SignInRequestData;
import com.ss.model.data.SignUpRequestData;
import com.ss.model.data.UserData;
import com.ss.model.dto.UserDto;

/**
 * @author sachin
 *
 */
@RestController
public class FINCRUserController {

	Logger logger = LoggerFactory.getLogger(FINCRUserController.class);

	@Autowired
	UserFacade userFacade;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/hello")
	public String hello() {
		return "Hello World";
	}

	@RequestMapping(value = "/auth/requestAccess", method = RequestMethod.POST)
	public ResponseEntity<FINCRResponseEntity<UserData>> createUser(@RequestBody SignUpRequestData signUpRequest) {
		FINCRResponseEntity<UserData> FINCRRespEntity = new FINCRResponseEntity<UserData>();
		try {
			UserData userData = userFacade.createUser(signUpRequest);
			FINCRRespEntity.setMessage("User added.");
			FINCRRespEntity.setData(userData);
		} catch (DuplicateEmailFoundExecption duplicateExecption) {
			FINCRRespEntity.setErrors("Email already present.");
			logger.error(String.format("User with email id: %s already exists.", signUpRequest.getUserName()));
			logger.error("Email already present." + duplicateExecption);
			return ResponseEntity.badRequest().body(FINCRRespEntity);
		}
		logger.info(String.format("User with email id: %s is Created, but profile is inactive.",
				signUpRequest.getUserName()));
		return ResponseEntity.status(HttpStatus.CREATED).body(FINCRRespEntity);
	}

	@RequestMapping(value = "/auth/login", method = RequestMethod.POST)
	public ResponseEntity<FINCRResponseEntity<UserData>> athenticateUser(@RequestBody SignInRequestData signInData)
			throws Exception {
		FINCRResponseEntity<UserData> FINCRRespEntity = new FINCRResponseEntity<UserData>();
		try {
			userFacade.evitUserFromCache(signInData.getUserName());
			User userDetails = userFacade.athenticateUser(signInData);
			UserData userData = userFacade.generateAthenticationToken(userDetails);
			FINCRRespEntity.setData(userData);
			FINCRRespEntity.setMessage("SuccessFully Athenticated");
		} catch (BadCredentialsException e) {
			FINCRRespEntity.setErrors("Incorrect username or password.");
			logger.error("Incorrect username or password." + e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(FINCRRespEntity);
		}
		return ResponseEntity.ok().body(FINCRRespEntity);
	}

	@RequestMapping(value = "/auth/logout", method = RequestMethod.POST)
	public ResponseEntity<FINCRResponseEntity<UserData>> signOut(@RequestBody SignInRequestData signInData) {
		FINCRResponseEntity<UserData> FINCRRespEntity = new FINCRResponseEntity<UserData>();
		try {
			userFacade.evitUserFromCache(signInData.getUserName());
			FINCRRespEntity.setMessage("SuccessFully logged out");
		} catch (Exception e) {
			FINCRRespEntity.setErrors("Error while logging user out.");
			logger.error("EhCache error while logging user out." + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FINCRRespEntity);
		}
		return ResponseEntity.ok().body(FINCRRespEntity);
	}

	@RequestMapping(value = "/auth/requestAccess", method = RequestMethod.GET)
	public ResponseEntity<FINCRResponseEntity<List<UserDto>>> getAllUserForRequestAccess() {
		FINCRResponseEntity<List<UserDto>> FINCRRespEntity = new FINCRResponseEntity<List<UserDto>>();
		List<UserData> userList = userFacade.getAllUserspendingForAccessGrant();
		FINCRRespEntity.setData(userList.stream().map(this::convertToDto).collect(Collectors.toList()));

		return ResponseEntity.ok().body(FINCRRespEntity);
	}
	
	@RequestMapping(value = "/auth/updateAccess", method = RequestMethod.POST)
	public ResponseEntity<FINCRResponseEntity<String>> grantAccess(@RequestBody List<UserDto> userDto) {
		FINCRResponseEntity<String> FINCRRespEntity = new FINCRResponseEntity<String>();
		List<UserData> userList = userDto.stream().map(this::convertToEntity).collect(Collectors.toList());
		int count = userFacade.updateGrantAccess(userList);
		logger.info(String.format("No. of user granted access is : %s", count));
		
		FINCRRespEntity.setMessage(String.format("Granted access to %s accounts.", count));
		return ResponseEntity.ok().body(FINCRRespEntity);
	}
	
	/**
	 * Function to create userDto object from UserData
	 * 
	 * @param userData
	 * @return {@link UserDto}
	 */
	private UserDto convertToDto(UserData userData) {
		UserDto userDto = modelMapper.map(userData, UserDto.class);
		return userDto;
	}
	
	private UserData convertToEntity(UserDto postDto) {
		UserData userData = modelMapper.map(postDto, UserData.class);
		return userData;
	}
}
