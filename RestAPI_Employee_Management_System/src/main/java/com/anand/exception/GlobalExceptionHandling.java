package com.anand.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anand.dto.APIResponse;

//Global exception handling for all controllers
@RestControllerAdvice 
public class GlobalExceptionHandling {

	//Handle all Exception in java
	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIResponse<Object>> handleException(Exception exc){
	     APIResponse<Object> response=new APIResponse<>();
	     response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()); //500 status
		 response.setMessage("Something Went Wrong!!"+exc.getMessage());
		 response.setData(null);
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
