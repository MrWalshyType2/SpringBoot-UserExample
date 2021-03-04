package com.example.demo.api.configuration;

import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.demo.api.data.domain.User;
import com.example.demo.api.data.domain.serializer.UserJsonDeserializer;
import com.example.demo.api.data.domain.serializer.UserJsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

// This way can be used to add the (de)serializer without the @JsonComponent annotation

//@Configuration
//public class JacksonConfiguration {
//
//	@Bean
//	@Primary
//	public ObjectMapper objectMapper() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		
//		JsonComponentModule jsonComponentModule = new JsonComponentModule();
//		jsonComponentModule.addSerializer(User.class, new UserJsonSerializer());
//		jsonComponentModule.addDeserializer(User.class, new UserJsonDeserializer());
//		
//		objectMapper.registerModule(jsonComponentModule);
//		return objectMapper;
//	}
//}
