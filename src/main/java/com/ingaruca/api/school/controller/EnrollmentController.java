package com.ingaruca.api.school.controller;

import com.ingaruca.api.school.application.mapper.CourseMapper;
import com.ingaruca.api.school.application.mapper.EnrollmentMapper;
import com.ingaruca.api.school.application.mapper.StudentMapper;
import com.ingaruca.api.school.controller.dto.request.EnrollmentRequestDto;
import com.ingaruca.api.school.controller.dto.response.CourseResponseDto;
import com.ingaruca.api.school.controller.dto.response.EnrollmentResponseDto;
import com.ingaruca.api.school.service.CourseService;
import com.ingaruca.api.school.service.EnrollmentService;
import com.ingaruca.api.school.service.StudentService;

import java.net.URI;
import java.util.stream.Collectors;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentMapper enrollmentMapper;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    @PostMapping
    public Mono<ResponseEntity<EnrollmentResponseDto>> save(@Valid @RequestBody EnrollmentRequestDto enrollmentRequestDto,
                                                            final ServerHttpRequest httpRequest) {
        return Mono.just(enrollmentRequestDto)
                .map(enrollmentMapper::toService)
                .flatMap(enrollmentService::save)
                .map(enrollmentMapper::fromService)
                .flatMap(enrollment -> studentService.findById(enrollment.getStudent().getId())
                        .map(studentMapper::fromService)
                        .map(student -> {
                            enrollment.setStudent(student);
                            return enrollment;
                        }))
                .flatMap(enrollment -> courseService.findByIds(enrollment.getCourses().stream()
                                .map(CourseResponseDto::getId)
                                .collect(Collectors.toList()))
                        .map(courseMapper::fromService)
                        .collectList()
                        .map(courses -> {
                            enrollment.setCourses(courses);
                            return enrollment;
                        }))
                .map(enrollment -> ResponseEntity.created(URI.create(httpRequest.getURI().toString().concat("/").concat(enrollment.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(enrollment));
    }

}