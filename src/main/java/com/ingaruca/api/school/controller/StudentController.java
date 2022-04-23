package com.ingaruca.api.school.controller;

import com.ingaruca.api.school.application.mapper.StudentMapper;
import com.ingaruca.api.school.controller.dto.request.StudentRequestDto;
import com.ingaruca.api.school.controller.dto.response.StudentResponseDto;
import com.ingaruca.api.school.model.Student;
import com.ingaruca.api.school.service.StudentService;

import java.net.URI;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;
    private final StudentMapper studentMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<StudentResponseDto>>> findAll() {
        Flux<StudentResponseDto> students = service.findAll()
                .map(studentMapper::fromService);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(students));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<StudentResponseDto>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(studentMapper::fromService)
                .map(student -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(student))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<StudentResponseDto>> save(@Valid @RequestBody StudentRequestDto studentRequestDto,
                                                         final ServerHttpRequest httpRequest) {
        return Mono.just(studentRequestDto)
                .map(studentMapper::toService)
                .flatMap(service::save)
                .map(studentMapper::fromService)
                .map(student -> ResponseEntity
                        .created(URI.create(httpRequest.getURI().toString().concat("/").concat(student.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(student));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<StudentResponseDto>> update(@PathVariable("id") String id,
                                                           @Valid @RequestBody StudentRequestDto studentRequestDto) {
        Mono<Student> studentRequest = Mono.just(studentRequestDto)
                .map(studentMapper::toService);
        return service.findById(id)
                .zipWith(studentRequest, (bd, s) -> {
                    bd.setId(id);
                    bd.setFirstName(s.getFirstName());
                    bd.setLastName(s.getLastName());
                    bd.setDni(s.getDni());
                    bd.setAge(s.getAge());
                    return bd;
                })
                .flatMap(service::update)
                .map(studentMapper::fromService)
                .map(student -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(student))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return service.findById(id)
                .flatMap(student -> service.delete(id)
                        .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
