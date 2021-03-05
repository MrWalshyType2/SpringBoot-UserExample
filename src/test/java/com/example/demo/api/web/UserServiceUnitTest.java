package com.example.demo.api.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
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

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
	
	@Captor
	private ArgumentCaptor<User> capturedUser;
	
	@BeforeEach
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
	
	@Test
	public void createUserTest() {
		when(userRepository.save(valid)).thenReturn(valid);
		
		UserDTO createdUser = userService.createUser(valid);
		
		assertEquals(validDTO, createdUser);
		verify(userRepository, times(1)).save(valid);
	}
	
	@Test
	public void updateUserTest() throws UserNotFoundException {
		User userWithUpdatedForename = new User(valid.getId(), "Fred", "Herbet", 
												"bob.herbet@email.com",
												AccountType.USER,
												new UserLogin("bobbyo", "password"));
		
		UserDTO updatedUserDTO = userMapper.mapToDTO(userWithUpdatedForename);
		
		when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(valid));
		when(userRepository.save(Mockito.any(User.class))).thenReturn(userWithUpdatedForename);
		
		UserDTO updatedUser = userService.updateUser(valid.getId(), valid);
	
		assertEquals(updatedUserDTO.getForename(), updatedUser.getForename());
		verify(userRepository, times(1)).findById(valid.getId());
		verify(userRepository, times(1)).save(valid);
	}
	
	@Test
	public void updateUserUserNotFoundTest() throws UserNotFoundException {
		when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
		
		UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> {
			userService.updateUser(valid.getId(), valid);
		});
		
		assertEquals("User not found", e.getMessage());
		verify(userRepository, times(1)).findById(valid.getId());
	}
	
	@Test
	public void updateUserLoginDetailsTest() throws UserNotFoundException {
		UserLoginDTO updatedLogin = new UserLoginDTO("bobbyo", "password123");
		
		User userWithUpdatedLogin = new User(valid.getId(), "Fred", "Herbet", 
											 "bob.herbet@email.com",
											 AccountType.USER,
											 userMapper.mapToUserLogin(updatedLogin));
		
		when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(valid));
		when(userRepository.save(Mockito.any(User.class))).thenReturn(userWithUpdatedLogin);
		
		UserDTO updatedUser = userService.updateUserLoginDetails(valid.getId(), updatedLogin);
	
		assertEquals(updatedUser.getUserLogin().getPassword(),
				     updatedLogin.getPassword());
		
		verify(userRepository, times(1)).findById(valid.getId());
		verify(userRepository, times(1)).save(valid);	
	}
	
	@Test
	public void deleteUserByIdTest() throws UserNotFoundException {
		when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(valid));
		when(userRepository.existsById(Mockito.any(UUID.class))).thenReturn(false);
		
		boolean success = userService.deleteUserById(valid.getId());
		
		assertEquals(true, success);
		
		verify(userRepository, times(1)).findById(valid.getId());
		verify(userRepository, times(1)).existsById(valid.getId());
	}
}
