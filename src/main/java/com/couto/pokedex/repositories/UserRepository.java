package com.couto.pokedex.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.couto.pokedex.entities.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long>{

	Mono<User> findByUsername(String username);

}
