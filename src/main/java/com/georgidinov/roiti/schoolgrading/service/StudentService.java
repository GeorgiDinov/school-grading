package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentListDTO;

public interface StudentService {

    StudentListDTO findAllStudents();

    StudentDTO findStudentById(Long id);

}
