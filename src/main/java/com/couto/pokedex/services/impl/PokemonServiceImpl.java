package com.couto.pokedex.services.impl;

import org.springframework.stereotype.Service;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.repositories.PokemonRepository;
import com.couto.pokedex.services.PokemonService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService{
	
	private final PokemonRepository pokemonRepository;

	@Override
	public Mono<Pokemon> save(Pokemon pokemon) {
		return pokemonRepository.save(pokemon);
	}

	@Override
	public Flux<Pokemon> findAll() {
		return pokemonRepository.findAll();
	}

	@Override
	public Mono<Pokemon> findById(Long id) {
		return pokemonRepository.findById(id);
	}

}
