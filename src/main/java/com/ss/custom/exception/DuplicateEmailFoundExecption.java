/**
 * 
 */
package com.ss.custom.exception;

/**
 * @author sachin
 *
 */
public class DuplicateEmailFoundExecption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2697148749466409450L;
	
	public DuplicateEmailFoundExecption(String message, Exception e)
	{
		super(message, e);
	}

	public DuplicateEmailFoundExecption(String message) {
		super(message);
	}
}
