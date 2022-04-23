package com.ingaruca.api.school.service.impl;

import com.ingaruca.api.school.model.Course;
import com.ingaruca.api.school.repository.CourseRepository;
import com.ingaruca.api.school.repository.common.GenericRepository;
import com.ingaruca.api.school.service.CourseService;
import com.ingaruca.api.school.service.impl.common.CrudImpl;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl extends CrudImpl<Course, String> implements CourseService {

    private final CourseRepository repository;

    @Override
    protected GenericRepository<Course, String> getRepository() {
        return repository;
    }

    @Override
    public Flux<Course> findByIds(List<String> ids) {
        return repository.findByIds(ids);
    }
}
