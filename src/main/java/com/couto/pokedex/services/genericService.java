package com.couto.pokedex.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface generic
public interface genericService<T, ID> {
	
	Mono<T> save(T entidade);

	Flux<T> findAll();
	
	Mono<T> findById(ID id);
	
	Mono<Void> update(T entidade);

	Mono<Void> delete(ID id);

}
