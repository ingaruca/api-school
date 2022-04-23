package com.ingaruca.api.school.controller.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CourseRequestDto {

    @NotNull
    @NotBlank(message = "Name cannot be empty")
    @Size(message = "Name must be between 4 and 50 characters", min = 4, max = 50)
    private String name;

    @NotNull
    @NotBlank(message = "Acronym cannot be empty")
    @Size(message = "Acronym must be between 4 and 8 characters", min = 4, max = 8)
    private String acronym;

    @NotNull
    private Boolean status;

}
