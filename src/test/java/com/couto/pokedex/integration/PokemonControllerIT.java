package com.couto.pokedex.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.repositories.PokemonRepository;
import com.couto.pokedex.services.impl.PokemonServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Classe para teste de integração
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import(PokemonServiceImpl.class)
public class PokemonControllerIT {

	@MockBean
	private PokemonRepository pokemonRepository;

	@Autowired
	private WebTestClient testClient;

	private final Pokemon pokemon = createValidUpdatePokemon();

	@BeforeEach
	public void setUp() {
		BDDMockito.when(pokemonRepository.findAll()).thenReturn(Flux.just(pokemon));

		BDDMockito.when(pokemonRepository.findByName(ArgumentMatchers.anyString())).thenReturn(Flux.just(pokemon));

		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.just(pokemon));

		BDDMockito.when(pokemonRepository.save(pokemon)).thenReturn(Mono.just(pokemon));

		BDDMockito.when(pokemonRepository.delete(pokemon)).thenReturn(Mono.empty());

	}

	@Test
	@DisplayName("findAll return flux of pokemon")
	void findAll_returnFluxOfPokemon_whenSuccessful_test() {
		testClient
			.get()
			.uri("/v1/pokemon")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.[0].id").isEqualTo(pokemon.getId())
			.jsonPath("$.[0].name").isEqualTo(pokemon.getName());
	}

	@Test
	@DisplayName("buscaPorNome return flux of pokemon")
	void buscaPorNome_returnFluxOfPokemon_whenSuccessful_test() {
		testClient
			.get()
			.uri("/v1/pokemon/name/{name}", "Charizard")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.[0].id").isEqualTo(pokemon.getId())
			.jsonPath("$.[0].name").isEqualTo(pokemon.getName());

	}

	@Test
	@DisplayName("findById return Mono of pokemon")
	void findById_returnMonoOfPokemon_whenSuccessful_test() {
		testClient
			.get()
			.uri("/v1/pokemon/{id}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(pokemon.getId())
			.jsonPath("$.name").isEqualTo(pokemon.getName());
	}
	
	@Test
	@DisplayName("findById return Mono of pokemon")
	void findById_returnMonoError_whenEmptyMonoIsReturned_test() {
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.empty());

		testClient
			.get()
			.uri("/v1/pokemon/{id}", 1L)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.status").isEqualTo(404);
	}


	
	@Test	  
	@DisplayName("save return Mono of Pokemon when pokemon is saved") 
	void save_returnMonoOfPokemon_whenMonoOfPokemonIsSaved_test() {
		testClient
			.post()
		  	.uri("/v1/pokemon")
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.body(BodyInserters.fromValue(pokemon))
		  	.exchange()
		  	.expectStatus().isCreated()
		  	.expectBody()
		  	.jsonPath("$.id").isEqualTo(pokemon.getId());
	  
	  }
	
	@Test	  
	@DisplayName("save Returns mono error with bad requenst when name is empty") 
	void save_returnErros_whenNameIsEmpty_test() {
		
		Pokemon pokemonNameNull = createValidUpdatePokemon();
		pokemonNameNull.setName(null);
		
		testClient
			.post()
		  	.uri("/v1/pokemon")
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.body(BodyInserters.fromValue(pokemonNameNull))
		  	.exchange()
		  	.expectStatus().isBadRequest()
		  	.expectBody()
		  	.jsonPath("$.status").isEqualTo(400);
	  
	  }
	 

	
	@Test	  
	@DisplayName("update return Mono Empty when pokemon is updated") 
	void update_returnMonoEmpty_whenMonoOfPokemonIsUpdated_test() {
		BDDMockito.when(pokemonRepository.save(pokemon)).thenReturn(Mono.empty());
		testClient
			.put()
			.uri("/v1/pokemon/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
		  	.body(BodyInserters.fromValue(pokemon))
			.exchange()
			.expectStatus().isNoContent();
		
	}
	 

	
	@Test	  
	@DisplayName("delete return Mono empty when pokemon is deleted") 
	void delete_returnMonoEmpty_whenMonoOfPokemonIsDelete_test() {
		testClient
			.delete()
			.uri("/v1/pokemon/{id}", 1L)
			.exchange()
			.expectStatus().isNoContent();

	}

	public static Pokemon createValidUpdatePokemon() {
		return Pokemon.builder().id(1L).name("Charizard").type("fire").build();
	}

}
