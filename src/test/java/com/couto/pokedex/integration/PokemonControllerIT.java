package com.couto.pokedex.integration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.repositories.PokemonRepository;
import com.couto.pokedex.services.PokemonService;

/**
 * Classe para teste de integração
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import(PokemonService.class)
public class PokemonControllerIT {

	@MockBean
	private PokemonRepository pokemonRepository;

	@Autowired
	private WebTestClient testClient;

	private final Pokemon pokemon = createValidUpdatePokemon();
	
	
	
	
	
	
	
	
	
	
	
	

	public static Pokemon createPokemonToBeSaved() {
		return Pokemon.builder().name("Charizard").type("fire").build();
	}

	public static Pokemon createValidPokemon() {
		return Pokemon.builder().id(1L).name("Charizard").type("fire").build();
	}

	public static Pokemon createValidUpdatePokemon() {
		return Pokemon.builder().id(1L).name("Charizard").type("fire").build();
	}

}
