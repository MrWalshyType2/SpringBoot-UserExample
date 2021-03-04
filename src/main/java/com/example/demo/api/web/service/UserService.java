package com.example.demo.api.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.api.data.domain.User;
import com.example.demo.api.data.domain.mapper.UserMapper;
import com.example.demo.api.data.repository.UserRepository;
import com.example.demo.api.web.domainDTO.UserDTO;
import com.example.demo.api.web.domainDTO.UserLoginDTO;
import com.example.demo.api.web.exception.UserNotFoundException;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	
	@Autowired
	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}
	
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTO> returnables = new ArrayList<UserDTO>();
		
		users.forEach(u -> 
			returnables.add(userMapper.mapToDTO(u)));
		
		return returnables;
	}
	
	public UserDTO getUserById(UUID id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		
		return userMapper.mapToDTO(user.orElseThrow(UserNotFoundException::new));
	}
	
	public UserDTO createUser(User user) {
		User savedUser = userRepository.save(user);
		
		return userMapper.mapToDTO(savedUser);
	}
	
	public UserDTO updateUser(UUID id, User user) throws UserNotFoundException {
		Optional<User> oldUser = userRepository.findById(id);
		
		if (oldUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		
		User userInDb = oldUser.get();
		userInDb.setForename(user.getForename());
		userInDb.setSurname(user.getSurname());
		userInDb.getUserLogin();
		userInDb.setEmail(user.getEmail());
		userInDb.getUserLogin().setUsername(user.getUserLogin().getUsername());
		userInDb.getUserLogin().setPassword(user.getUserLogin().getPassword());
		
		User updated = userRepository.save(userInDb);
		
		return userMapper.mapToDTO(updated);
	}
	
	public UserDTO updateUserLoginDetails(UUID id, @RequestBody UserLoginDTO userLoginDTO) throws UserNotFoundException {
		Optional<User> oldUser = userRepository.findById(id);
		
		if (oldUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		
		User userInDb = oldUser.get();
		userInDb.setUserLogin(userMapper.mapToUserLogin(userLoginDTO));
		
		User updated = userRepository.save(userInDb);
		
		return userMapper.mapToDTO(updated);
	}
	
	public boolean deleteUserById(UUID id) throws UserNotFoundException {
		Optional<User> oldUser = userRepository.findById(id);
		
		if (oldUser.isEmpty()) {
			throw new UserNotFoundException();
		}
		userRepository.deleteById(id);
		
		return userRepository.existsById(id);
	}
}
