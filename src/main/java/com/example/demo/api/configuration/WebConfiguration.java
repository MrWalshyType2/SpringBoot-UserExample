package com.example.demo.api.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	
}
