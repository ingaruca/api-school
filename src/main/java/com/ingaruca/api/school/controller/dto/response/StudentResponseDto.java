package com.ingaruca.api.school.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StudentResponseDto {

    private String id;
    private String firstName;
    private String lastName;
    private String dni;
    private Integer age;

}
