package com.ingaruca.api.school.handler;

import com.ingaruca.api.school.application.mapper.StudentMapper;
import com.ingaruca.api.school.controller.dto.response.StudentResponseDto;
import com.ingaruca.api.school.model.Student;
import com.ingaruca.api.school.service.StudentService;

import java.util.Comparator;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class StudentHandler {

    private final StudentService service;
    private final StudentMapper mapper;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll()
                        .sort(Comparator.comparing(Student::getAge).reversed())
                        .map(mapper::fromService), StudentResponseDto.class);
    }

}
