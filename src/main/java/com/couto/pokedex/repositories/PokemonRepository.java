package com.couto.pokedex.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.couto.pokedex.entities.Pokemon;

public interface PokemonRepository extends ReactiveCrudRepository<Pokemon, Long>{

}
