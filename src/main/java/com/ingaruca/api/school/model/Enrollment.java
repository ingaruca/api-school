package com.ingaruca.api.school.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "enrollments")
public class Enrollment {

    @Id
    private String id;
    private LocalDate createdDate;
    private Student student;
    private List<Course> courses;
    private Boolean status = Boolean.TRUE;

}
