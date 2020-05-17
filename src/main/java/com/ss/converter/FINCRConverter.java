/**
 * 
 */
package com.ss.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * @author sachin
 * @param <S>
 * @param <T>
 *
 */
public interface FINCRConverter<S, T> extends Converter<S, T> {

	public default Object createNewInstance(Class<T> t) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		Class cls = Class.forName(t.getName()); 
		return cls.newInstance();
		
	}
}
