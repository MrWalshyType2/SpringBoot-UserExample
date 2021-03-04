package com.example.demo.api.data.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false, unique = true)
	private UUID id;
	
	private String forename;
	private String surname;
	
	private String username;
	private String password;
	private String email;
	
	private AccountType accountType;
	
	public User(String forename, String surname, String username, String password, String email,
			AccountType accountType) {
		super();
		this.forename = forename;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.accountType = accountType;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}
