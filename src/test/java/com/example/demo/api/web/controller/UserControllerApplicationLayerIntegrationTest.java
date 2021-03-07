package com.example.demo.api.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.api.data.domain.AccountType;
import com.example.demo.api.data.domain.User;
import com.example.demo.api.data.domain.UserLogin;
import com.example.demo.api.data.repository.UserRepository;
import com.example.demo.api.web.domainDTO.UserDTO;
import com.example.demo.api.web.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest // Does not start the server unless port is specified
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerApplicationLayerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
	public void setup() {
		valid = new User("Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));

		valid2 = new User("Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));

		valid3 = new User("Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));
		
		validUsers = List.of(valid, valid2, valid3);
		
		List<User> savedUsers = userRepository.saveAll(validUsers);
		
		validDTO = new UserDTO(savedUsers.get(0));
		validDTO2 = new UserDTO(savedUsers.get(1));
		validDTO3 = new UserDTO(savedUsers.get(2));
		validUserDTOs = List.of(validDTO, validDTO2, validDTO3);
	}
	
	@Test
	public void getAllUsers_ShouldReturn_ValidListOfUserDTOs_FromTestDatabase() throws JsonProcessingException, Exception {
		MockHttpServletResponse response = mockMvc.perform(get("/user")
							.accept(MediaType.APPLICATION_JSON))
						.andDo(print())
						.andReturn().getResponse();
		
		assertThat(response.getStatus())
			.isEqualTo(HttpStatus.OK.value());
				
		assertThat(response.getContentAsString())
			.isEqualTo(objectMapper.writeValueAsString(validUserDTOs));
	}
}