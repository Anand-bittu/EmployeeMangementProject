package com.anand.dto;

import org.springframework.stereotype.Component;

@Component
/*
 * <T>: This is a type parameter.
 *  It makes the APIResponse class generic, meaning it can "wrap" any type of data.
 *   T is a placeholder that will be replaced by
 *   a concrete type (e.g., Employee, List<Client>, String, etc.) 
 * when you create an instance of APIResponse.
 */
public class APIResponse <T>{
    
	private int statusCode;
	private String message;
	/*
	 * This declares a private field named 
	 * Data with the generic type T
	 * this is the primary payload of your API response. 
	 * It will hold the actual data you want to send back to the client. 
	 * Because T is generic, 
	 * it can be a single object (e.g., an Employee object after a creation),
	 *  a list of objects (e.g., List<Employee> for a fetch-all operation),
	 *   or even null if the operation doesn't return specific data (e.g., a successful deletion).
	 */
	  private T data;
	
	public APIResponse() {
       System.out.println("APIResponse");
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
