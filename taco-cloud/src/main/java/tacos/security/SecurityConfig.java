package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.headers(header -> header.frameOptions(f -> f.disable()))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/design", "/orders").hasRole("USER")
//				.requestMatchers("/management/**").hasRole("USER")
				.anyRequest().permitAll())
		.formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/design", true))
		.logout(l -> l.logoutSuccessUrl("/"))
		.httpBasic(Customizer.withDefaults())
		.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new NoEncodingPasswordEncoder();
	}
}
