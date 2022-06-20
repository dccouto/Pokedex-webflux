package com.couto.pokedex.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.exceptions.PokedexException;
import com.couto.pokedex.repositories.PokemonRepository;
import com.couto.pokedex.services.PokemonService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

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
		return pokemonRepository.findById(id)
				.switchIfEmpty(Mono.error(new PokedexException(HttpStatus.NOT_FOUND, "Pokemon not found")));
	}

	@Override
	public Flux<Pokemon> buscaPorNome(String name) {
		return pokemonRepository.findByName(name);
	}

	@Override
	public Mono<Void> update(Pokemon pokemon){
		return findById(pokemon.getId())
				.map(pokemonFound -> pokemon.withId(pokemonFound.getId()))
				.flatMap(pokemonRepository::save)
				.then();
	}
	
	@Override
	public Mono<Void> delete(Long id){
		return findById(id)
				.flatMap(pokemonRepository::delete)
				.then();
	}

}
