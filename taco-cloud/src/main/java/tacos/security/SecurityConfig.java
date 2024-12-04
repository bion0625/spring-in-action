package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/design", "/orders").hasRole("USER")
				.anyRequest().permitAll())
		.httpBasic(Customizer.withDefaults())
		.build();
	}
	
	@Bean
	public UserDetailsService users() {
		UserDetails user1 = User.builder()
				.username("user1")
				.password("{noop}password1")
				.roles("USER")
				.build();
		UserDetails user2 = User.builder()
				.username("user2")
				.password("{noop}password2")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user1, user2);
	}
}
