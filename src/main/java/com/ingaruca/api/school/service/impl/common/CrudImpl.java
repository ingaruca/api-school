package com.ingaruca.api.school.service.impl.common;

import com.ingaruca.api.school.repository.common.GenericRepository;
import com.ingaruca.api.school.service.common.Crud;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CrudImpl<T, I> implements Crud<T, I> {

    protected abstract GenericRepository<T, I> getRepository();

    @Override
    public Mono<T> save(T t) {
        return getRepository().save(t);
    }

    @Override
    public Mono<T> update(T t) {
        return getRepository().save(t);
    }

    @Override
    public Flux<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Mono<T> findById(I id) {
        return getRepository().findById(id);
    }

    @Override
    public Mono<Void> delete(I id) {
        return getRepository().deleteById(id);
    }
}
