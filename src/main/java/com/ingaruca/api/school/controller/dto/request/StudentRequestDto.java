package com.ingaruca.api.school.controller.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StudentRequestDto {

    @NotNull
    @NotBlank(message = "First name cannot be empty")
    @Size(message = "First name must be between 3 and 25 characters", min = 3, max = 25)
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name cannot be empty")
    @Size(message = "Last name must be between 3 and 25 characters", min = 3, max = 25)
    private String lastName;

    @NotNull
    @NotBlank(message = "DNI cannot be empty")
    @Pattern(regexp = "^[0-9]{8}$", message = "DNI must be 8-digit numeric characters")
    private String dni;

    @NotNull
    @Min(value = 3, message = "Required min age is 3")
    @Max(value = 60, message = "Required max age is 60")
    private Integer age;

}
