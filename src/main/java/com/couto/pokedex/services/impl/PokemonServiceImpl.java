package com.couto.pokedex.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
		return pokemonRepository.save(pokemon).log();
	}

	@Override
	public Flux<Pokemon> findAll() {
		return pokemonRepository.findAll();
	}

	@Override
	public Mono<Pokemon> findById(Long id) {
		return pokemonRepository
				.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found")))
				.log();
	}

	@Override
	public Flux<Pokemon> buscaPorNome(String name) {
		return pokemonRepository.findByName(name);
	}

}
