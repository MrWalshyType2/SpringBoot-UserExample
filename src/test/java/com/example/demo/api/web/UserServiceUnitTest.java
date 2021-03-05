package com.example.demo.api.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.api.data.domain.AccountType;
import com.example.demo.api.data.domain.User;
import com.example.demo.api.data.domain.UserLogin;
import com.example.demo.api.data.domain.mapper.UserMapper;
import com.example.demo.api.data.repository.UserRepository;
import com.example.demo.api.web.domainDTO.UserDTO;
import com.example.demo.api.web.domainDTO.UserLoginDTO;
import com.example.demo.api.web.exception.UserNotFoundException;
import com.example.demo.api.web.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserServiceUnitTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@MockBean
	private UserRepository userRepository;
	
	private User valid;
	private User valid2;
	private User valid3;
	private User invalid;
	private List<User> validUsers;
	
	private UserDTO validDTO;
	private UserDTO validDTO2;
	private UserDTO validDTO3;
	private List<UserDTO> validUserDTOs;
	
	@BeforeAll
	public void init() {
		valid = new User(UUID.randomUUID(), "Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));
		
		validDTO = userMapper.mapToDTO(valid);
		
		valid2 = new User(UUID.randomUUID(), "Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));
		
		validDTO2 = userMapper.mapToDTO(valid2);
		
		valid3 = new User(UUID.randomUUID(), "Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));
		
		validDTO3 = userMapper.mapToDTO(valid3);
		
		validUsers = List.of(valid, valid2, valid3);
		validUserDTOs = List.of(validDTO, validDTO2, validDTO3);
	}
	
	@Test
	public void getAllUsersTest() {
		when(userRepository.findAll()).thenReturn(validUsers);
		
		List<UserDTO> users = userService.getAllUsers();
		
		assertEquals(validUserDTOs, users);
		verify(userRepository, times(1)).findAll();
	}
	
	@Test
	public void getUserByIdTest() throws UserNotFoundException {
		when(userRepository.findById(valid.getId())).thenReturn(Optional.of(valid));
		
		UserDTO userDTO = userService.getUserById(valid.getId());
		
		assertEquals(validDTO, userDTO);
		verify(userRepository, times(1)).findById(valid.getId());
	}
}
