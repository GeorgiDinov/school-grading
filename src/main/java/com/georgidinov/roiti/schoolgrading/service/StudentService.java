package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;

public interface StudentService {

    StudentListDTO findAllStudents();

    StudentDTO findStudentById(Long id);

    StudentDTO saveStudent(StudentDTO studentDTO) throws EntityValidationException;

    StudentDTO updateStudent(Long id, StudentDTO studentDTO) throws EntityValidationException;

    void deleteStudentById(Long id);

}
