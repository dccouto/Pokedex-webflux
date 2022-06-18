package com.couto.pokedex.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.couto.pokedex.entities.Pokemon;

import reactor.core.publisher.Flux;

public interface PokemonRepository extends ReactiveCrudRepository<Pokemon, Long>{

	Flux<Pokemon> findByName(String name);

}
