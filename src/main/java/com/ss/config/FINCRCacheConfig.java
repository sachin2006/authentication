/**
 * 
 */
package com.ss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

/**
 * @author sachin
 *
 */
@Configuration
public class FINCRCacheConfig {
	
	@Autowired
	CacheManager cacheManager;
	
	@Bean
	public UserCache configUserCache()
	{
		UserCache usercache = new SpringCacheBasedUserCache(cacheManager.getCache("userCache"));
		return usercache;
	}
}
