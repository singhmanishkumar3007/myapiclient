package com.tech.boot.myclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

@Configuration
@EnableAutoConfiguration()
@ComponentScan({ "com.tech.boot.myclient" })
public class MyClientApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(MyClientApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(MyClientApplication.class, args);

		LOGGER.info("HI Rajesh,logging here, in info mode ");
		LOGGER.debug("in debug mode");
		LOGGER.error("in error mode");

	}

	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
