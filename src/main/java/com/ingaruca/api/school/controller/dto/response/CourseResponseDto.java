package com.ingaruca.api.school.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CourseResponseDto {

    private String id;
    private String name;
    private String acronym;
    private Boolean status;

}
