package com.ingaruca.api.school.service.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Crud<T, I> {

    Mono<T> save(T t);
    Mono<T> update(T t);
    Flux<T> findAll();
    Mono<T> findById(I id);
    Mono<Void> delete(I id);

}