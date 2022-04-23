package com.ingaruca.api.school.repository.common;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository <T, I> extends ReactiveMongoRepository<T, I> {
}
