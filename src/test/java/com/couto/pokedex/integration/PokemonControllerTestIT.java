package com.couto.pokedex.integration;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.repositories.PokemonRepository;
import com.couto.pokedex.util.WebTestClientUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Classe para teste de integração
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PokemonControllerTestIT {

	@MockBean
	private PokemonRepository pokemonRepository;

	@Autowired
	private WebTestClientUtil webTestClientUtil;
	
	private WebTestClient testClientUser;
	private WebTestClient testClientAdmin;
	private WebTestClient testClientInvalid;

	private final Pokemon pokemon = createValidUpdatePokemon();

	@BeforeEach
	public void setUp() {
		this.testClientUser = webTestClientUtil.authenticateClient("Fulano", "123");
		this.testClientAdmin = webTestClientUtil.authenticateClient("Diego", "123");
		this.testClientInvalid = webTestClientUtil.authenticateClient("Errado", "123");
		
		
		BDDMockito.when(pokemonRepository.findAll()).thenReturn(Flux.just(pokemon));

		BDDMockito.when(pokemonRepository.findByName(ArgumentMatchers.anyString())).thenReturn(Flux.just(pokemon));

		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.just(pokemon));

		BDDMockito.when(pokemonRepository.save(pokemon)).thenReturn(Mono.just(pokemon));
		
		BDDMockito.when(pokemonRepository.saveAll(List.of(pokemon))).thenReturn(Flux.just(pokemon));

		BDDMockito.when(pokemonRepository.delete(pokemon)).thenReturn(Mono.empty());

	}

	@Test
	@DisplayName("findAll return flux of pokemon when user is successfully authenticated and has role ADMIN")
	void findAll_returnFluxOfPokemon_whenSuccessful_test() {
		testClientAdmin
			.get()
			.uri("/v1/pokemon")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.[0].id").isEqualTo(pokemon.getId())
			.jsonPath("$.[0].name").isEqualTo(pokemon.getName());
	}
	
	@Test
	@DisplayName("findAll return forbidden when user is successfully authenticated and does'nt have role ADMIN")
	void findAll_returnForbidden_whenUserDoesNotHaveRoleAdmin_test() {
		testClientUser
		.get()
		.uri("/v1/pokemon")
		.exchange()
		.expectStatus().isForbidden();
	}
	
	@Test
	@DisplayName("findAll return unauthorized when user is not authenticated")
	void findAll_returnUnauthorized_whenUserIsNotAuthenticated_test() {
		testClientInvalid
		.get()
		.uri("/v1/pokemon")
		.exchange()
		.expectStatus().isUnauthorized();
	}

	@Test
	@DisplayName("buscaPorNome return flux of pokemon when user is successfully authenticated and has role USER")
	void buscaPorNome_returnFluxOfPokemon_whenUserIsAuthenticatedSuccessful_test() {
		testClientUser
			.get()
			.uri("/v1/pokemon/name/{name}", "Charizard")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.[0].id").isEqualTo(pokemon.getId())
			.jsonPath("$.[0].name").isEqualTo(pokemon.getName());

	}
	
	@Test
	@DisplayName("buscaPorNome return flux of pokemon when user is successfully authenticated and has role ADMIN")
	void buscaPorNome_returnFluxOfPokemon_whenAdminIsAuthenticatedSuccessful_test() {
		testClientAdmin
		.get()
		.uri("/v1/pokemon/name/{name}", "Charizard")
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.[0].id").isEqualTo(pokemon.getId())
		.jsonPath("$.[0].name").isEqualTo(pokemon.getName());
		
	}
	
	@Test
	@DisplayName("buscaPorNome return unauthorized when user is not authenticated")
	void buscaPorNome_returnUnauthorized_whenUserIsNotAuthenticated_test() {
		testClientInvalid
		.get()
		.uri("/v1/pokemon/name/{name}", "Charizard")
		.exchange()
		.expectStatus().isUnauthorized();
		
	}

	@Test
	@DisplayName("findById return Mono of pokemon when user is successfully authenticated and has role ADMIN")
	void findById_returnMonoOfPokemon_whenAdminIsAuthenticatedSuccessful_test() {
		testClientAdmin
			.get()
			.uri("/v1/pokemon/{id}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(pokemon.getId())
			.jsonPath("$.name").isEqualTo(pokemon.getName());
	}
	@Test
	@DisplayName("findById return Mono of pokemon when user is successfully authenticated and has role USER")
	void findById_returnMonoOfPokemon_whenUserIsAuthenticatedSuccessful_test() {
		testClientUser
		.get()
		.uri("/v1/pokemon/{id}", 1L)
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.id").isEqualTo(pokemon.getId())
		.jsonPath("$.name").isEqualTo(pokemon.getName());
	}
	@Test
	@DisplayName("findById return unauthorized when user is not authenticated")
	void findById_returnUnauthorized_whenUserIsNotAuthenticated_test() {
		testClientInvalid
		.get()
		.uri("/v1/pokemon/{id}", 1L)
		.exchange()
		.expectStatus().isUnauthorized();
	}
	
	@Test
	@DisplayName("findById return Mono of pokemon")
	void findById_returnMonoError_whenEmptyMonoIsReturned_test() {
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.empty());

		testClientUser
			.get()
			.uri("/v1/pokemon/{id}", 1L)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.status").isEqualTo(404);
	}


	
	@Test	  
	@DisplayName("save return Mono of Pokemon when pokemon is saved and user is successfully authenticated and has role ADMIN") 
	void save_returnMonoOfPokemon_whenMonoOfPokemonIsSavedAndAdminIsAuthenticatedSuccessful_test() {
		testClientAdmin
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
	@DisplayName("save Returns mono error with bad requenst when name is empty user is successfully authenticated and has role ADMIN") 
	void save_returnErros_whenNameIsEmpty_test() {
		
		Pokemon pokemonNameNull = createValidUpdatePokemon();
		pokemonNameNull.setName(null);
		
		testClientAdmin
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
	@DisplayName("update return Mono Empty when pokemon is updated user is successfully authenticated and has role ADMIN") 
	void update_returnMonoEmpty_whenMonoOfPokemonIsUpdated_test() {
		BDDMockito.when(pokemonRepository.save(pokemon)).thenReturn(Mono.empty());
		testClientAdmin
			.put()
			.uri("/v1/pokemon/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
		  	.body(BodyInserters.fromValue(pokemon))
			.exchange()
			.expectStatus().isNoContent();
		
	}
	
	@Test	  
	@DisplayName("update return Mono Erros when pokemon not exist and user is successfully authenticated and has role ADMIN") 
	void update_returnMonoErros_whenPokemonNotExist_test() {
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.empty());
		testClientAdmin
		.put()
		.uri("/v1/pokemon/{id}", 1L)
		.contentType(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromValue(pokemon))
		.exchange()
		.expectStatus().isNotFound();
		
	}
	
	
	
	
	
	@Test	  
	@DisplayName("save Batch return Flux of Pokemon when list of pokemon is saved user is successfully authenticated and has role ADMIN") 
	void save_returnFluxOfPokemon_whenListOfPokemonIsSaved_test() {
		testClientAdmin
			.post()
		  	.uri("/v1/pokemon/batch")
		  	.contentType(MediaType.APPLICATION_JSON)
		  	.body(BodyInserters.fromValue(List.of(pokemon)))
		  	.exchange()
		  	.expectStatus().isCreated()
		  	.expectBody()
		  	.jsonPath("$.[0].id").isEqualTo(pokemon.getId());
	  
	  }
	
	 

	
	@Test	  
	@DisplayName("delete return Mono empty when pokemon is deleted user is successfully authenticated and has role ADMIN") 
	void delete_returnMonoEmpty_whenMonoOfPokemonIsDelete_test() {
		testClientAdmin
			.delete()
			.uri("/v1/pokemon/{id}", 1L)
			.exchange()
			.expectStatus().isNoContent();

	}
	
	@Test	  
	@DisplayName("delete return Mono Erros with pokemon not found user is successfully authenticated and has role ADMIN") 
	void delete_returnMonoErros_whenPokemonNotExist_test() {
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.empty());
		testClientAdmin
			.delete()
			.uri("/v1/pokemon/{id}", 1L)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.status").isEqualTo(404);

	}

	public static Pokemon createValidUpdatePokemon() {
		return Pokemon.builder().id(1L).name("Charizard").type("fire").build();
	}

}
