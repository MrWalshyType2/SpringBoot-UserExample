package com.example.demo.api.data.domain.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.api.data.domain.User;
import com.example.demo.api.data.domain.UserLogin;
import com.example.demo.api.web.domain.UserDTO;
import com.example.demo.api.web.domain.UserLoginDTO;

@Component
public class UserMapper {

	private ModelMapper mapper;
	
	@Autowired
	public UserMapper(ModelMapper mapper) {
		super();
		this.mapper = mapper;
	}
	
	public UserDTO mapToDTO(User user) {
		return this.mapper.map(user, UserDTO.class);
	}
	
	public User mapToUser(UserDTO userDTO) {
		return this.mapper.map(userDTO, User.class);
	}

	public UserLogin mapToUserLogin(UserLoginDTO userLoginDTO) {
		return this.mapper.map(userLoginDTO, UserLogin.class);
	}
	
	public UserLoginDTO mapToUserLoginDTO(UserLogin userLogin) {
		return this.mapper.map(userLogin, UserLoginDTO.class);
	}
}
