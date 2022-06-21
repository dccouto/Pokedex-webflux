package com.couto.pokedex.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.services.PokemonService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PokemonHandler {

	private final PokemonService pokemonService;

	public Mono<ServerResponse> savePokemon(ServerRequest request) {
		Mono<Pokemon> pokemon = request.bodyToMono(Pokemon.class);
		return ServerResponse.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(pokemon.flatMap(pokemonService::save), Pokemon.class);

	}
	
	public Mono<ServerResponse> findAllPokemon() {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(pokemonService.findAll(), Pokemon.class);
		
	}
	

	public Mono<ServerResponse> findPokemonById(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(pokemonService.findById(id), Pokemon.class);
		
	}
	
	public Mono<ServerResponse> findPokemonByName(ServerRequest request) {
		String name = request.pathVariable("name");
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(pokemonService.buscaPorNome(name), Pokemon.class);
		
	}
	
	
	
	public Mono<ServerResponse> deletePokemon(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));
		pokemonService.delete(id).subscribe();
		return ServerResponse.status(HttpStatus.NO_CONTENT)
		.build();
		
	}
}
