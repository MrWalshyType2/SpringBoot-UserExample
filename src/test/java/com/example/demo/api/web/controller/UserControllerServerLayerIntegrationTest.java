package com.example.demo.api.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.api.data.domain.AccountType;
import com.example.demo.api.data.domain.User;
import com.example.demo.api.data.domain.UserLogin;
import com.example.demo.api.web.domainDTO.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Write data insert in data.sql

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // This starts the server
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Sql(scripts = { "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerServerLayerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
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
	
	@BeforeEach
	public void init() {
		valid = new User(UUID.randomUUID(), "Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));
		
		validDTO = new UserDTO(valid);
		
		valid2 = new User(UUID.randomUUID(), "Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));
		
		validDTO2 = new UserDTO(valid2);
		
		valid3 = new User(UUID.randomUUID(), "Bob", "Herbet", 
				 "bob.herbet@email.com",
				 AccountType.USER,
				 new UserLogin("bobbyo", "password"));
		
		validDTO3 = new UserDTO(valid3);
		
		validUsers = List.of(valid, valid2, valid3);
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
