package app.reacter;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(
				exchange -> exchange
				.pathMatchers("/design", "/orders").authenticated()
				.anyExchange().permitAll());
		return http.build();
	}
	
	@Bean
	ReactiveUserDetailsService userDetailsService() {
		return new ReactiveUserDetailsService() {
			
			@Override
			public Mono<UserDetails> findByUsername(String username) {
				// TODO Auto-generated method stub
				return Mono.just(username).map(name -> {
					return new UserDetails() {
						
						@Override
						public String getUsername() {
							return name;
						}
						
						@Override
						public String getPassword() {
							return name;
						}
						
						@Override
						public Collection<? extends GrantedAuthority> getAuthorities() {
							return null;
						}
					};
				});
			}
		};
	}
}
