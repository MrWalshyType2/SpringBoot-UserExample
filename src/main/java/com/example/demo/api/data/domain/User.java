package com.example.demo.api.data.domain;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Builder
@EqualsAndHashCode
public class User {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(length = 255, columnDefinition = "varchar(255)", updatable = false, nullable = false, unique = true)
	private UUID id;
	
	private String forename;
	private String surname;
	
	private String email;
	
	private AccountType accountType;
	
	@Type(type = "VARCHAR(255)")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fkUserLoginId")
	private UserLogin userLogin;
	
	public User(String forename, String surname, String email, AccountType accountType, UserLogin userLogin) {
		super();
		this.forename = forename;
		this.surname = surname;
		this.email = email;
		this.accountType = accountType;
		this.userLogin = userLogin;
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

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

}
