package com.couto.pokedex.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.services.PokemonService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {

	private final PokemonService pokemonService;

	@PostMapping
	public Mono<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
		return pokemonService.save(pokemon);

	}
	
	@GetMapping
	public Flux<Pokemon> findAllPokemon() {
		return pokemonService.findAll();
		
	}

}
