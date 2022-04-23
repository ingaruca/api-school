package com.ingaruca.api.school.controller.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EnrollmentResponseDto {

    private String id;
    private String createdDate;
    private StudentResponseDto student;
    private List<CourseResponseDto> courses;
    private Boolean status;

}
