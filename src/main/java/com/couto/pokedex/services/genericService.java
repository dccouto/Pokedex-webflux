package com.couto.pokedex.services;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface generic
public interface GenericService<T, ID> {
	
	Mono<T> save(T objeto);
	
	Flux<T> saveAll(List<T> objetos);

	Flux<T> findAll();
	
	Mono<T> findById(ID id);
	
	Mono<Void> update(T objeto);

	Mono<Void> delete(ID id);

}
