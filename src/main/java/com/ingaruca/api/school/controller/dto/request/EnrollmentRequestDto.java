package com.ingaruca.api.school.controller.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EnrollmentRequestDto {

    @NotNull
    @NotBlank(message = "studentId cannot be empty")
    private String studentId;

    @NotNull
    private List<String> listCourses;

}
