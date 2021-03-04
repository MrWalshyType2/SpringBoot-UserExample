package com.example.demo.api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundException() {
		return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
	}
}
