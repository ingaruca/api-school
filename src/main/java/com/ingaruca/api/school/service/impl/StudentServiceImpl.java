package com.ingaruca.api.school.service.impl;

import com.ingaruca.api.school.model.Student;
import com.ingaruca.api.school.repository.StudentRepository;
import com.ingaruca.api.school.repository.common.GenericRepository;
import com.ingaruca.api.school.service.StudentService;
import com.ingaruca.api.school.service.impl.common.CrudImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl extends CrudImpl<Student, String> implements StudentService {

    private final StudentRepository repository;

    @Override
    protected GenericRepository<Student, String> getRepository() {
        return repository;
    }
}
