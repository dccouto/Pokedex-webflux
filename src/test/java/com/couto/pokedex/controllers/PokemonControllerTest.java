package com.couto.pokedex.controllers;

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
import com.couto.pokedex.services.PokemonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class PokemonControllerTest {

	@InjectMocks
	private PokemonController pokemonController;

	@Mock
	private PokemonService pokemonService;

	private static Pokemon pokemonValid = createValidPokemon();
	private static Pokemon pokemonToBeSaved = createPokemonToBeSaved();
	private static Pokemon pokemonValidUpdate = createValidUpdatePokemon();

	@BeforeEach
	public void setUp() {
		BDDMockito.when(pokemonService.findAll()).thenReturn(Flux.just(pokemonValid));

		BDDMockito.when(pokemonService.findById(ArgumentMatchers.anyLong())).thenReturn(Mono.just(pokemonValid));

		BDDMockito.when(pokemonService.buscaPorNome(ArgumentMatchers.anyString())).thenReturn(Flux.just(pokemonValid));

		BDDMockito.when(pokemonService.save(pokemonToBeSaved)).thenReturn(Mono.just(pokemonValid));
		
		BDDMockito.when(pokemonService.saveAll(List.of(pokemonToBeSaved))).thenReturn(Flux.just(pokemonValid));

		BDDMockito.when(pokemonService.delete(ArgumentMatchers.anyLong())).thenReturn(Mono.empty());
		
		BDDMockito.when(pokemonService.update(pokemonValidUpdate)).thenReturn(Mono.empty());

	}

	@Test
	@DisplayName("findAll return flux of pokemon")
	void findAll_returnFluxOfPokemon_whenSuccessful_test() {
		StepVerifier.create(pokemonController.findAllPokemon())
				.expectSubscription()
				.expectNext(pokemonValid)
				.verifyComplete();

	}

	@Test
	@DisplayName("buscaPorNome return flux of pokemon")
	void buscaPorNome_returnFluxOfPokemon_whenSuccessful_test() {
		StepVerifier.create(pokemonController.findPokemon(ArgumentMatchers.anyString()))
				.expectSubscription()
				.expectNext(pokemonValid).verifyComplete();

	}

	@Test
	@DisplayName("findById return Mono of pokemon")
	void findById_returnMonoOfPokemon_whenSuccessful_test() {
		StepVerifier.create(pokemonController.findPokemon(ArgumentMatchers.anyLong())).expectSubscription()
				.expectNext(pokemonValid)
				.verifyComplete();

	}

	@Test
	@DisplayName("save return Mono of Pokemon when pokemon is saved")
	void save_returnMonoOfPokemon_whenMonoOfPokemonIsSaved_test() {

		StepVerifier.create(pokemonController.savePokemon(pokemonToBeSaved))
			.expectSubscription()
			.expectNext(pokemonValid)
			.verifyComplete();

	}
	
	@Test
	@DisplayName("saveBatch return Flux of Pokemon when List of pokemon is saved")
	void saveBatch_returnFluxOfPokemon_whenListOfPokemonIsSaved_test() {
		
		StepVerifier.create(pokemonController.saveBatchPokemon(List.of(pokemonToBeSaved)))
			.expectSubscription()
			.expectNext(pokemonValid)
			.verifyComplete();
		
	}

	@Test
	@DisplayName("update return Mono Empty when pokemon is updated")
	void update_returnMonoEmpty_whenMonoOfPokemonIsUpdated_test() {

		StepVerifier.create(pokemonController.updatePokemon(1L, pokemonValidUpdate))
				.expectSubscription()
				.verifyComplete();

	}

	@Test
	@DisplayName("delete return Mono empty when pokemon is deleted")
	void delete_returnMonoEmpty_whenMonoOfPokemonIsDelete_test() {

		StepVerifier.create(pokemonController.deletePokemon(ArgumentMatchers.anyLong()))
				.expectSubscription()
				.verifyComplete();

	}

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
