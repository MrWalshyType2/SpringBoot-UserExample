package com.example.demo.api.data.domain.serializer;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.example.demo.api.data.domain.User;
import com.example.demo.api.web.domain.UserDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class UserJsonSerializer extends JsonSerializer<User> {

	@Override
	public void serialize(User value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		
		if (!value.getId().toString().isEmpty()) {
			gen.writeStringField("id", value.getId().toString());
		}
		gen.writeStringField("forename", value.getForename());
		gen.writeStringField("surname", value.getSurname());
		gen.writeStringField("username", value.getUsername());
		gen.writeStringField("password", value.getPassword());
		gen.writeStringField("email", value.getEmail());
		gen.writeStringField("accountType", value.getAccountType().toString());
		
		gen.writeEndObject();
	}
	
}
