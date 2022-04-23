package com.ingaruca.api.school.service.impl;

import com.ingaruca.api.school.model.Enrollment;
import com.ingaruca.api.school.repository.EnrollmentRepository;
import com.ingaruca.api.school.repository.common.GenericRepository;
import com.ingaruca.api.school.service.EnrollmentService;
import com.ingaruca.api.school.service.impl.common.CrudImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EnrollmentServiceImpl extends CrudImpl<Enrollment, String> implements EnrollmentService {

    private final EnrollmentRepository repository;

    @Override
    protected GenericRepository<Enrollment, String> getRepository() {
        return repository;
    }
}
