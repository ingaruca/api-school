package com.ingaruca.api.school.application.mapper;

import com.ingaruca.api.school.controller.dto.request.StudentRequestDto;
import com.ingaruca.api.school.controller.dto.response.StudentResponseDto;
import com.ingaruca.api.school.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toService(StudentRequestDto studentRequestDto);

    StudentResponseDto fromService(Student student);

}
