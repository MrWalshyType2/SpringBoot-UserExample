package com.example.demo.api.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.api.data.repository.UserRepository;
import com.example.demo.api.web.controller.UserController;
import com.example.demo.api.web.exception.UserExceptionControllerAdvice;
import com.example.demo.api.web.service.UserService;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private UserController userController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserExceptionControllerAdvice userExceptionControllerAdvice;
	
	@Test
	public void userContextLoads() throws Exception {
		assertThat(userController).isNotNull();
		assertThat(userService).isNotNull();
		assertThat(userRepository).isNotNull();
		assertThat(userExceptionControllerAdvice).isNotNull();
	}
}
