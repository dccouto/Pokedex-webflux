package com.couto.pokedex.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.services.PokemonService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/pokemon")
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
	
	@GetMapping("/{id}")
	public Mono<Pokemon> findPokemon(@PathVariable long id) {
		return pokemonService.findById(id);
		
	}
	
	@GetMapping("/name/{name}")
	public Flux<Pokemon> findPokemon(@PathVariable String name) {
		return pokemonService.buscaPorNome(name);
		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> updatePokemon(@PathVariable Long id, @RequestBody Pokemon pokemon) {
		return pokemonService.update(pokemon.withId(id));

	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deletePokemon(@PathVariable Long id) {
		return pokemonService.delete(id);
		
	}

}
