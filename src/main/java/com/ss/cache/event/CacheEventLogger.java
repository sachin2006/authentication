/**
 * 
 */
package com.ss.cache.event;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class used to log user  
 * @author sachin
 *
 */
public class CacheEventLogger implements CacheEventListener<String, UserDetails> {

	Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);

	@Override
	public void onEvent(CacheEvent<? extends String, ? extends UserDetails> cacheEvent) {
		logger.info("Cache event triggered");
		if(logger.isDebugEnabled())
			logger.info("User cache %s: Old Value %s -> New Value %s",cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
	}	
}
