package com.tewodros_mekonnen.springdemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerRestExceptionHandler {

	// adding an exception handler for CustomerNotFoundException
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException exc) {
		
		// create CustomerErrorResponse object
		CustomerErrorResponse error = new CustomerErrorResponse(
				                                                 HttpStatus.NOT_FOUND.value(), 
				                                                 exc.getMessage(), 
				                                                 System.currentTimeMillis());
		// Return ResponseEntity
		return new ResponseEntity<> (error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(Exception exc) {
		
		// create CustomerErrorResponse object
		CustomerErrorResponse error = new CustomerErrorResponse(
				                                                 HttpStatus.BAD_REQUEST.value(), 
				                                                 exc.getMessage(), 
				                                                 System.currentTimeMillis());
		// Return ResponseEntity
		return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);
	}

}
