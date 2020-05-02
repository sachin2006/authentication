/**
 * 
 */
package com.ss.model.data;

/**
 * @author sachin
 * @param <T>
 *
 */
public class FINCRResponseEntity<T> {
	private String message;
	private String errors;
	private T data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
