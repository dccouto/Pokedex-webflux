package com.couto.pokedex.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.couto.pokedex.entities.Pokemon;
import com.couto.pokedex.exceptions.PokedexException;
import com.couto.pokedex.repositories.PokemonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class PokemonServiceImplTest {
	
	private static Pokemon pokemonValid = createValidPokemon();
	private static Pokemon pokemonToBeSaved = createPokemonToBeSaved();
	
	@InjectMocks
	private PokemonServiceImpl pokemonService;
	
	@Mock
	private PokemonRepository pokemonRepository;


	@BeforeEach
	public void setUp() {
		BDDMockito.when(pokemonRepository.findAll())
			.thenReturn(Flux.just(pokemonValid));
		
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Mono.just(pokemonValid));
		
		BDDMockito.when(pokemonRepository.findByName(ArgumentMatchers.anyString()))
			.thenReturn(Flux.just(pokemonValid));
	}
	
	@Test
	@DisplayName("findAll return flux of pokemon")
	void findAll_returnFluxOfPokemon_whenSuccessful_test() {
		StepVerifier.create(pokemonService.findAll())
			.expectSubscription()
			.expectNext(pokemonValid)
			.verifyComplete();
		
	}
	
	@Test
	@DisplayName("buscaPorNome return flux of pokemon")
	void buscaPorNome_returnFluxOfPokemon_whenSuccessful_test() {
		StepVerifier.create(pokemonService.buscaPorNome(ArgumentMatchers.anyString()))
			.expectSubscription()
			.expectNext(pokemonValid)
			.verifyComplete();
		
	}
	
	@Test
	@DisplayName("findById return Mono of pokemon")
	void findById_returnMonoOfPokemon_whenSuccessful_test() {
		StepVerifier.create(pokemonService.findById(ArgumentMatchers.anyLong()))
			.expectSubscription()
			.expectNext(pokemonValid)
			.verifyComplete();
		
	}
	
	@Test
	@DisplayName("findById return Mono error when pokemon does not exist")
	void findById_returnMonoOfPokemon_whenEmptyMonoIsReturned_test() {
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Mono.empty());
		
		StepVerifier.create(pokemonService.findById(ArgumentMatchers.anyLong()))
			.expectSubscription()
			.expectError(PokedexException.class)
			.verify();
		
	}

	public static Pokemon createPokemonToBeSaved() {
		return Pokemon.builder()
				.name("Charizard")
				.type("fire")
				.build();
	}
	
	public static Pokemon createValidPokemon() {
		return Pokemon.builder()
				.id(1L)
				.name("Charizard")
				.type("fire")
				.build();
	}
	
	public static Pokemon createValidUpdatePokemon() {
		return Pokemon.builder()
				.id(1L)
				.name("Charizard")
				.type("fire")
				.build();
	}
}
