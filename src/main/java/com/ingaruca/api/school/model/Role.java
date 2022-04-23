package com.ingaruca.api.school.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "roles")
public class Role {

    @Id
    private String id;
    private String name;

}
