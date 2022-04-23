package com.ingaruca.api.school.application.security;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
	
	private String token;
	private Date expiration;

}
