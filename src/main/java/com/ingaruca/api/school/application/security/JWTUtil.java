package com.ingaruca.api.school.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil implements Serializable{

	@Value("${jjwt.secret}")
	private String secret;
	
	@Value("${jjwt.expiration}")
	private String expirationTime;
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(AuthUser authUser) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", authUser.getRoles());
		claims.put("test", "probando....");
		return doGenerateToken(claims, authUser.getUsername());
	}
	
	private String doGenerateToken(Map<String, Object> claims, String username) {
		Long expirationTimeLong = Long.parseLong(expirationTime);
		
		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
		
		SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());
		
		return Jwts.builder()
				   .setClaims(claims)
				   .setSubject(username)
				   .setIssuedAt(createdDate)
				   .setExpiration(expirationDate)
				   .signWith(key)
				   .compact();		
	}
	
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
