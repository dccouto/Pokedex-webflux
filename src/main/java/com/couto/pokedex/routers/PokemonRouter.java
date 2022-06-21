package com.couto.pokedex.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.couto.pokedex.handlers.PokemonHandler;

@Component
public class PokemonRouter {

	@Bean
	public RouterFunction<ServerResponse> router(PokemonHandler pokemonHandler) {

		RouterFunction<ServerResponse> routePokemon = route()
				.POST("/pokemon", pokemonHandler::savePokemon)
				//.GET("/pokemon", accept(MediaType.APPLICATION_JSON), pokemonHandler::findAllPokemon)
				.GET("/pokemon/name/{name}", accept(MediaType.APPLICATION_JSON), pokemonHandler::findPokemonByName)
				.GET("/pokemon/{id}", accept(MediaType.APPLICATION_JSON), pokemonHandler::findPokemonById)
				//.PUT("/pokemon/{id}", accept(MediaType.APPLICATION_JSON), pokemonHandler::updatePokemon)
				.DELETE("/pokemon/{id}", accept(MediaType.APPLICATION_JSON), pokemonHandler::deletePokemon)
				.build();

		return routePokemon;
	}
}
