package com.ingaruca.api.school.application.security;

import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	private final JWTUtil jwtUtil;
	
	@Override	
	public Mono<Authentication> authenticate(Authentication authentication) {
		String token = authentication.getCredentials().toString();
		
		String usuario;
		try {
			usuario = jwtUtil.getUsernameFromToken(token);
		} catch (Exception e) {
			usuario = null;
		}
		
		if (usuario != null && jwtUtil.validateToken(token)) {
			Claims claims = jwtUtil.getAllClaimsFromToken(token);
			
			List<String> rolesMap = claims.get("roles", List.class);
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				usuario,
				null,
				rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
			);
			return Mono.just(auth);
		} else {
			return Mono.error(new InterruptedException("Invalid or expired token"));
		}
	}
}

