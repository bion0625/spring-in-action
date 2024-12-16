package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(request -> request
				.requestMatchers("/instances", "/management/**", "/login").permitAll()
				.anyRequest().authenticated());
		http.formLogin(form -> form.defaultSuccessUrl("/"));
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
