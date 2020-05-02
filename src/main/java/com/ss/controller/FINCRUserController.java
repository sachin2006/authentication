/**
 * 
 */
package com.ss.controller;

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

/**
 * @author sachin
 *
 */
@RestController
public class FINCRUserController {

	Logger logger = LoggerFactory.getLogger(FINCRUserController.class);
	
	@Autowired
	UserFacade userFacade;

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
		logger.info(String.format("User with email id: %s is Created, but profile is inactive.", signUpRequest.getUserName()));
		return ResponseEntity.status(HttpStatus.CREATED).body(FINCRRespEntity);
	}

	@RequestMapping(value = "/auth/login", method = RequestMethod.POST)
	public ResponseEntity<FINCRResponseEntity<UserData>> athenticateUser(@RequestBody SignInRequestData signInData)
			throws Exception {
		FINCRResponseEntity<UserData> FINCRRespEntity = new FINCRResponseEntity<UserData>();
		try {
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
}
