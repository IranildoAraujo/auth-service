package com.authservice.security;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountCredentialsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	
	public AccountCredentialsVO() {}
	
}
