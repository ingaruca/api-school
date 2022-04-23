package com.ingaruca.api.school.repository;

import com.ingaruca.api.school.model.Course;
import com.ingaruca.api.school.repository.common.GenericRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import reactor.core.publisher.Flux;

public interface CourseRepository extends GenericRepository<Course, String> {

    @Query("{_id: { $in: ?0 } }")
    Flux<Course> findByIds(List<String> ids);

}