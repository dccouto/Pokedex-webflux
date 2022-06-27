package com.couto.pokedex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.couto.pokedex.services.impl.UserPokedexDetailsServiceImpl;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity //Para habilitar no controller o @PreAuthorize
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		// @formatter:off
		return http
				.csrf()
				.disable()
				.authorizeExchange()
					.pathMatchers(HttpMethod.POST, "/pokemon/**").hasRole("ADMIN")
					.pathMatchers(HttpMethod.POST, "/v1/pokemon/**").hasRole("ADMIN")
					
					.pathMatchers(HttpMethod.PUT, "/pokemon/**").hasRole("ADMIN")
					.pathMatchers(HttpMethod.PUT, "/v1/pokemon/**").hasRole("ADMIN")
					
					.pathMatchers(HttpMethod.DELETE, "/pokemon/**").hasRole("ADMIN")
					.pathMatchers(HttpMethod.DELETE, "/v1/pokemon/**").hasRole("ADMIN")
					
					.pathMatchers(HttpMethod.GET, "/pokemon/**").hasRole("USER")
					.pathMatchers(HttpMethod.GET, "/v1/pokemon/**").hasRole("USER")
				.anyExchange().authenticated()
				.and()
					.httpBasic()
				.and()
					.build();
 
		// @formatter:on

	}
	
	@Bean
	public ReactiveAuthenticationManager authenticationManager(UserPokedexDetailsServiceImpl user) {
		return new UserDetailsRepositoryReactiveAuthenticationManager(user);
	}
}
