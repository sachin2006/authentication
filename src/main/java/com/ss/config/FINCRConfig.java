/**
 * 
 */
package com.ss.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sachin
 *
 */
@Configuration
public class FINCRConfig {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
}
