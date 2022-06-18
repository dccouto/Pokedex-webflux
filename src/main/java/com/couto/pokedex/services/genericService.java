package com.couto.pokedex.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface genericService<T, ID> {
	
	Mono<T> save(T pokemon);

	Flux<T> findAll();
	
	Mono<T> findById(ID id);

}
