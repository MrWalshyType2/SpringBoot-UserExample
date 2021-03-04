package com.example.demo.api.data.domain.serializer;

import java.io.IOException;
import java.util.UUID;

import org.springframework.boot.jackson.JsonComponent;

import com.example.demo.api.data.domain.AccountType;
import com.example.demo.api.data.domain.User;
import com.example.demo.api.data.domain.UserLogin;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

@JsonComponent
public class UserJsonDeserializer extends JsonDeserializer<User> {

	@Override
	public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		TreeNode tree = p.getCodec()
						 .readTree(p);
		
		TextNode id = (TextNode) tree.get("id");
		TextNode forename = (TextNode) tree.get("forename");
		TextNode surname = (TextNode) tree.get("surname");
		TextNode email = (TextNode) tree.get("email");
		TextNode accountType = (TextNode) tree.get("accountType");
		
		ObjectNode userLogin = (ObjectNode) tree.get("userLogin");

		User user;
		
		if (id == null || id.isEmpty()) {
			user = User.builder()
					.forename(forename.asText())
					.surname(surname.asText())
					.email(email.asText())
					.accountType(AccountType.valueOf(accountType.asText()))
					.userLogin(new UserLogin(userLogin.get("username").asText(),
											 userLogin.get("password").asText()))
					.build();
		} else {
			user = User.builder()
					.id(UUID.fromString(id.asText()))
					.forename(forename.asText())
					.surname(surname.asText())
					.email(email.asText())
					.accountType(AccountType.valueOf(accountType.asText()))
					.userLogin(new UserLogin(userLogin.get("username").asText(),
							 				 userLogin.get("password").asText()))
					.build();
		}
		return user;
	}

}
