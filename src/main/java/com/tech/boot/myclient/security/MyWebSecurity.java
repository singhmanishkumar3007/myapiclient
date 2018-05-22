package com.tech.boot.myclient.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public MyWebSecurity(UserDetailsService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "sign_up_url").permitAll()
		
		.antMatchers(HttpMethod.POST, "/login").permitAll()
		
		
				.anyRequest().authenticated()
				
				.and()
				//.addFilter(new MyAuthenticationFilter(authenticationManager()))
				.addFilter(new MyAuthorizationFilter(authenticationManager()))
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration=new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

}
