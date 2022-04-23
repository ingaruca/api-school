package com.ingaruca.api.school.controller;

import com.ingaruca.api.school.application.security.AuthRequest;
import com.ingaruca.api.school.application.security.AuthResponse;
import com.ingaruca.api.school.application.security.ErrorLogin;
import com.ingaruca.api.school.application.security.JWTUtil;
import com.ingaruca.api.school.service.UserService;

import java.util.Date;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class LoginController {

	private final JWTUtil jwtUtil;
	private final UserService service;
	
	@PostMapping("/login")
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest){
		return service.findByUsername(authRequest.getUsername())
				.map(userDetails -> {
				
					if(BCrypt.checkpw(authRequest.getPassword(), userDetails.getPassword())) {
						String token = jwtUtil.generateToken(userDetails);
						Date expiracion = jwtUtil.getExpirationDateFromToken(token);
						
						return ResponseEntity.ok(new AuthResponse(token, expiracion));
					}else {
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
								.body(new ErrorLogin("wrong credentials", new Date()));
					}
				}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
