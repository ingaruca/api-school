package com.ingaruca.api.school.application.mapper;

import com.ingaruca.api.school.controller.dto.request.CourseRequestDto;
import com.ingaruca.api.school.controller.dto.response.CourseResponseDto;
import com.ingaruca.api.school.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toService(CourseRequestDto requestDto);

    CourseResponseDto fromService(Course course);

}
