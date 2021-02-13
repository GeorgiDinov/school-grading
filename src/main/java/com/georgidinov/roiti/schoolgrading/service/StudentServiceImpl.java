package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.StudentMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentListDTO;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
import com.georgidinov.roiti.schoolgrading.validation.BaseNamedEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_STUDENT_NOT_FOUND;


@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    //== fields ==
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final BaseNamedEntityValidator baseNamedEntityValidator;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              StudentMapper studentMapper,
                              BaseNamedEntityValidator baseNamedEntityValidator) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.baseNamedEntityValidator = baseNamedEntityValidator;
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

    @Override
    public StudentDTO saveStudent(StudentDTO studentDTO) throws EntityValidationException {
        log.info("StudentServiceImpl::saveStudent -> studentDTO passed = {}", studentDTO);
        this.baseNamedEntityValidator.validate(studentDTO);
        if (isStudentNameExists(studentDTO)) {
            throw new RuntimeException("Student Exists...");//todo custom exception
        }
        return this.saveStudentToDatabase(this.studentMapper.studentDTOToStudent(studentDTO));
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) throws EntityValidationException {
        log.info("StudentServiceImpl::updateStudent -> id passed = {}, studentDTO passed = {}", id, studentDTO);
        this.baseNamedEntityValidator.validate(studentDTO);
        if (isStudentNameExists(studentDTO)) {
            throw new RuntimeException("Student Exists...");//todo custom exception
        }
        Student student = this.studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_STUDENT_NOT_FOUND, id))
                );
        student.setName(studentDTO.getName());
        return this.saveStudentToDatabase(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        log.info("StudentServiceImpl::deleteStudentById -> id passed = {}", id);
        this.studentRepository.deleteById(id);
    }

    //== private methods ==
    private StudentDTO saveStudentToDatabase(Student student) {
        Student savedStudent = this.studentRepository.save(student);
        return this.studentMapper.studentToStudentDTO(savedStudent);
    }

    private boolean isStudentNameExists(StudentDTO studentDTO) {
        Optional<Student> studentOptional = this.studentRepository.findStudentByName(studentDTO.getName());
        return studentOptional.isPresent();
    }

}