package com.ingaruca.api.school.repository;

import com.ingaruca.api.school.model.User;
import com.ingaruca.api.school.repository.common.GenericRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends GenericRepository<User, String> {

    Mono<User> findOneByUsername(String username);

}
