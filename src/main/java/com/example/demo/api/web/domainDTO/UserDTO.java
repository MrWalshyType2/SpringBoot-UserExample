package com.example.demo.api.web.domainDTO;

import java.util.UUID;

import com.example.demo.api.data.domain.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserDTO {

	private UUID id;
	
	private String forename;
	private String surname;
	
	private String email;
	
	private AccountType accountType;
	private UserLoginDTO userLogin;

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UserLoginDTO getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLoginDTO userLogin) {
		this.userLogin = userLogin;
	}
	
}
