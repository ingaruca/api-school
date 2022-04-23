package com.ingaruca.api.school.application.mapper;

import com.ingaruca.api.school.controller.dto.request.EnrollmentRequestDto;
import com.ingaruca.api.school.controller.dto.response.EnrollmentResponseDto;
import com.ingaruca.api.school.model.Course;
import com.ingaruca.api.school.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDate.class
})
public interface EnrollmentMapper {

    @Mapping(target = "student.id", source = "studentId")
    @Mapping(target = "courses", source = "listCourses", qualifiedByName = "setIdsToCourses")
    @Mapping(target = "createdDate", expression = "java(LocalDate.now())")
    Enrollment toService(EnrollmentRequestDto enrollmentRequestDto);

    @Named("setIdsToCourses")
    default List<Course> setIdsToCourses(List<String> listCourses) {
        return listCourses.stream()
                .map(id -> {
                    Course course = new Course();
                    course.setId(id);
                    return course;
                })
                .collect(Collectors.toList());
    }

    @Mapping(target = "createdDate", source = "createdDate", dateFormat = "yyyy-MM-dd")
    EnrollmentResponseDto fromService(Enrollment enrollment);

}