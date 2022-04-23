package com.ingaruca.api.school.controller;

import com.ingaruca.api.school.application.mapper.CourseMapper;
import com.ingaruca.api.school.controller.dto.request.CourseRequestDto;
import com.ingaruca.api.school.controller.dto.response.CourseResponseDto;
import com.ingaruca.api.school.model.Course;
import com.ingaruca.api.school.service.CourseService;

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
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;
    private final CourseMapper courseMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<CourseResponseDto>>> findAll() {
        Flux<CourseResponseDto> courses = service.findAll()
                .map(courseMapper::fromService);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(courses));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CourseResponseDto>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(courseMapper::fromService)
                .map(course -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<CourseResponseDto>> save(@Valid @RequestBody CourseRequestDto courseRequestDto,
                                                        final ServerHttpRequest httpRequest) {
        return Mono.just(courseRequestDto)
                .map(courseMapper::toService)
                .flatMap(service::save)
                .map(courseMapper::fromService)
                .map(course -> ResponseEntity
                        .created(URI.create(httpRequest.getURI().toString().concat("/").concat(course.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CourseResponseDto>> update(@PathVariable("id") String id,
                                                          @Valid @RequestBody CourseRequestDto courseRequestDto) {
        Mono<Course> courseRequest = Mono.just(courseRequestDto)
                .map(courseMapper::toService);
        return service.findById(id)
                .zipWith(courseRequest, (bd, c) -> {
                    bd.setId(id);
                    bd.setName(c.getName());
                    bd.setAcronym(c.getAcronym());
                    bd.setStatus(c.getStatus());
                    return bd;
                })
                .flatMap(service::save)
                .map(courseMapper::fromService)
                .map(course -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(course))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return service.findById(id)
                .flatMap(course -> {
                    course.setStatus(Boolean.FALSE);
                    return service.save(course)
                            .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
