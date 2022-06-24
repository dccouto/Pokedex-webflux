package com.couto.pokedex.services.impl;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
	private static Pokemon pokemonValidUpdate = createValidUpdatePokemon();
	
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
		
		BDDMockito.when(pokemonRepository.save(pokemonToBeSaved))
			.thenReturn(Mono.just(pokemonValid));
		
		BDDMockito.when(pokemonRepository.saveAll(List.of(pokemonToBeSaved, pokemonToBeSaved)))
			.thenReturn(Flux.just(pokemonValid, pokemonValid));
		
		BDDMockito.when(pokemonRepository.delete(pokemonValid))
			.thenReturn(Mono.empty());
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
	void findById_returnMonoError_whenEmptyMonoIsReturned_test() {
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Mono.empty());
		
		StepVerifier.create(pokemonService.findById(ArgumentMatchers.anyLong()))
			.expectSubscription()
			.expectError(PokedexException.class)
			.verify();
		
	}
	
	@Test
	@DisplayName("save return Mono of Pokemon when pokemon is saved")
	void save_returnMonoOfPokemon_whenMonoOfPokemonIsSaved_test() {
		
		StepVerifier.create(pokemonService.save(pokemonToBeSaved))
			.expectSubscription()
			.expectNext(pokemonValid)
			.verifyComplete();
		
	}
	
	@Test
	@DisplayName("update return Mono Empty when pokemon is updated")
	void update_returnMonoEmpty_whenMonoOfPokemonIsUpdated_test() {

		BDDMockito.when(pokemonRepository.save(pokemonValidUpdate))
			.thenReturn(Mono.empty());
		
		StepVerifier.create(pokemonService.update(pokemonValidUpdate))
			.expectSubscription()
			.verifyComplete();
		
	}
	
	@Test
	@DisplayName("save batch of Pokemon return Flux of Pokemon when list pokemon is saved")
	void save_returnFluxOfPokemon_whenListOfPokemonIsSaved_test() {
		
		StepVerifier.create(pokemonService.saveAll(List.of(pokemonToBeSaved, pokemonToBeSaved)))
			.expectSubscription()
			.expectNext(pokemonValid, pokemonValid)
			.verifyComplete();
		
	}
	
	@Test
	@DisplayName("update return Exception when pokemon dont extist")
	void update_returnMonoErros_whenEmptyMonoIsReturned_test() {
		
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Mono.empty());
		
		StepVerifier.create(pokemonService.update(pokemonValidUpdate))
			.expectSubscription()
			.expectError(PokedexException.class)
			.verify();
		
	}
	
	@Test
	@DisplayName("delete return Mono empty when pokemon is deleted")
	void delete_returnMonoEmpty_whenMonoOfPokemonIsDelete_test() {
		
		StepVerifier.create(pokemonService.delete(ArgumentMatchers.anyLong()))
			.expectSubscription()
			.verifyComplete();
		
	}
	
	@Test
	@DisplayName("delete return Exception when pokemon dont extist")
	void delete_returnMonoErros_whenEmptyMonoIsReturned_test() {
		
		BDDMockito.when(pokemonRepository.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Mono.empty());
		
		StepVerifier.create(pokemonService.delete(ArgumentMatchers.anyLong()))
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
