package com.tech.boot.myclient.security;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tech.boot.myclient.entity.MyEmployees;
import com.tech.boot.myclient.repository.MyEmployeesRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class MyLoginController {

	String SECRET = "SecretKeyToGenJWTs";
	long EXPIRATION_TIME = 864_000_000; // 10 days
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
	String SIGN_UP_URL = "/users/sign-up";

	@Autowired
	private MyEmployeesRepository myEmployeesRepository;

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public String login(@RequestBody LoginForm loginForm) throws ServletException {
		MyEmployees myemployee = myEmployeesRepository.findByUserNameAndPassword(loginForm.getUsername(),
				loginForm.getPassword());
		if (null != myemployee && null != myemployee.getUserName() && null != myemployee.getPassword()) {
			return generateMyToken(loginForm.getUsername());
		} else {
			throw new ServletException("Invalid Credentials");
		}
	}

	private String generateMyToken(String userName) {

		String webToken = Jwts.builder().setSubject(userName).setIssuer(userName)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
		return webToken;
	}

}
