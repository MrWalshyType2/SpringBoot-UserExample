package com.example.demo.api.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.api.data.domain.AccountType;
import com.example.demo.api.data.domain.User;
import com.example.demo.api.data.domain.UserLogin;
import com.example.demo.api.data.domain.mapper.UserMapper;
import com.example.demo.api.web.domainDTO.UserDTO;
import com.example.demo.api.web.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class) // Instantiates only the Web Layer without the whole context
public class UserControllerWebLayerMockIntegrationTest {

//	@LocalServerPort
//	private int port;
	
//	@Autowired
//	private TestRestTemplate restTemplate;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private JacksonTester<List<UserDTO>> userListMapper;
	
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
	public void getAllUsers_ShouldReturn_ValidListOfUserDTOs_FromService() throws JsonProcessingException, Exception {
		when(userService.getAllUsers()).thenReturn(validUserDTOs);
		
		MockHttpServletResponse response = mockMvc.perform(get("/user")
							.accept(MediaType.APPLICATION_JSON))
						.andDo(print())
						.andReturn().getResponse();
		
		assertThat(response.getStatus())
			.isEqualTo(HttpStatus.OK.value());
				
		assertThat(response.getContentAsString())
			.isEqualTo(objectMapper.writeValueAsString(validUserDTOs));
	
		verify(userService, times(1)).getAllUsers();
	}
}
