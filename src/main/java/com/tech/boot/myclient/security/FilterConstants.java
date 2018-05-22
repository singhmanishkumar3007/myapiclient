package com.tech.boot.myclient.security;

public interface FilterConstants {

	String SECRET = "SecretKeyToGenJWTs";
	long EXPIRATION_TIME = 864_000_000; // 10 days
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
	String SIGN_UP_URL = "/users/sign-up";

}