package com.example.demo.api.web.controller;

import java.util.List;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.data.domain.User;
import com.example.demo.api.web.domainDTO.UserDTO;
import com.example.demo.api.web.domainDTO.UserLoginDTO;
import com.example.demo.api.web.exception.UserNotFoundException;
import com.example.demo.api.web.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable("id") UUID id) throws UserNotFoundException {
		return userService.getUserById(id);
	}
	
	@PostMapping
	public UserDTO createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PutMapping("/{id}")
	public UserDTO updateUser(@PathVariable("id") UUID id, @RequestBody User user) throws UserNotFoundException {
		return userService.updateUser(id, user);
	}
	
	@PatchMapping
	public UserDTO updateUserLoginDetails(@PathParam("id") UUID id, @RequestBody UserLoginDTO user) throws UserNotFoundException {
		return userService.updateUserLoginDetails(id, user);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteUserById(@PathVariable UUID id) throws UserNotFoundException {
		return userService.deleteUserById(id);
	}
}
