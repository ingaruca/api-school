package com.ingaruca.api.school.service;

import com.ingaruca.api.school.model.Course;
import com.ingaruca.api.school.service.common.Crud;

import java.util.List;

import reactor.core.publisher.Flux;

public interface CourseService extends Crud<Course, String> {

    Flux<Course> findByIds(List<String> ids);

}
