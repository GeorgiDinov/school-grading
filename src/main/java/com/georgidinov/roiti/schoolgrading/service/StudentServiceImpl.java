package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.StudentMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_STUDENT_NOT_FOUND;


@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    //== fields ==
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentListDTO findAllStudents() {
        log.info("StudentServiceImpl::findAllStudents");
        return new StudentListDTO(
                this.studentRepository.findAll()
                        .stream()
                        .map(studentMapper::studentToStudentDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public StudentDTO findStudentById(Long id) {
        log.info("StudentServiceImpl::findStudentById -> id passed = {}", id);
        return this.studentRepository
                .findById(id)
                .map(studentMapper::studentToStudentDTO)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_STUDENT_NOT_FOUND, id)
                ));
    }
}
