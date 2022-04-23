package com.ingaruca.api.school.service;

import com.ingaruca.api.school.application.security.AuthUser;
import com.ingaruca.api.school.model.User;
import com.ingaruca.api.school.service.common.Crud;

import reactor.core.publisher.Mono;

public interface UserService extends Crud<User, String> {

    Mono<AuthUser> findByUsername(String username);

}
