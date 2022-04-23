package com.ingaruca.api.school.service.impl;

import com.ingaruca.api.school.application.security.AuthUser;
import com.ingaruca.api.school.model.User;
import com.ingaruca.api.school.repository.RoleRepository;
import com.ingaruca.api.school.repository.UserRepository;
import com.ingaruca.api.school.repository.common.GenericRepository;
import com.ingaruca.api.school.service.UserService;
import com.ingaruca.api.school.service.impl.common.CrudImpl;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends CrudImpl<User, String> implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public Mono<AuthUser> findByUsername(String username) {
        List<String> roles = new ArrayList<>();
        return userRepository.findOneByUsername(username)
                .flatMap(user -> Flux.fromIterable(user.getRoles())
                        .flatMap(role -> roleRepository.findById(role.getId())
                                .map(r -> {
                                    roles.add(r.getName());
                                    return r;
                                })).collectList()
                        .flatMap(list -> {
                            user.setRoles(list);
                            return Mono.just(user);
                        })
                        .flatMap(u -> Mono.just(new AuthUser(u.getUsername(), u.getPassword(), u.getStatus(), roles))));
    }

    @Override
    protected GenericRepository<User, String> getRepository() {
        return userRepository;
    }
}
