/**
 * 
 */
package com.ss.populator;

/**
 * @author sachin
 * Generic Populator to populate target data.
 */
public interface Populator<S, T> {

	/**
	 * Function to populate target object
	 * @param source
	 * @param target
	 * @return <T>
	 * */
	public T populate(S source,T target);
}
