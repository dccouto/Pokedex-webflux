package com.couto.pokedex.services.impl;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.couto.pokedex.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserPokedexDetailsServiceImpl implements ReactiveUserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userRepository.findByUsername(username)
					.cast(UserDetails.class);
	}

}
