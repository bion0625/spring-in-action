package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
				.headers(header -> header.frameOptions(f -> f.disable()))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/design", "/orders").hasRole("USER")
				.anyRequest().permitAll())
		.httpBasic(Customizer.withDefaults())
		.build();
	}
	
	@Bean
	public UserDetailsService users() {
		JdbcUserDetailsManager detailsManager = new JdbcUserDetailsManager(dataSource);
		detailsManager.setUsersByUsernameQuery("select username, password, enabled from users where username = ?");
		detailsManager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username = ?");
		return detailsManager;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new NoEncodingPasswordEncoder();
	}
}
