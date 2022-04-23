package com.ingaruca.api.school.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private Boolean status;
    private List<Role> roles;

}
