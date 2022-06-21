package com.couto.pokedex.services;

import com.couto.pokedex.entities.Pokemon;

import reactor.core.publisher.Flux;


public interface PokemonService extends genericService<Pokemon, Long> {
	
	Flux<Pokemon> buscaPorNome(String name);

}
