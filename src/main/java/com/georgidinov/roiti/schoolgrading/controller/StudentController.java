package com.georgidinov.roiti.schoolgrading.controller;


import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.STUDENT_BASE_URL;

@Slf4j
@RestController
@RequestMapping(STUDENT_BASE_URL)
public class StudentController {

    //== fields ==
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO findAllStudents() {
        log.info("StudentController::findAllStudents");
        return this.studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO findStudentById(@PathVariable String id) {
        log.info("StudentController::findStudentById -> id passed = {}", id);
        return this.studentService.findStudentById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO saveStudent(@RequestBody StudentDTO studentDTO) throws EntityValidationException {
        log.info("StudentController::saveStudent -> studentDTO passed = {}", studentDTO);
        return this.studentService.saveStudent(studentDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable String id,
                                    @RequestBody StudentDTO studentDTO) throws EntityValidationException {
        log.info("StudentController::updateStudent -> id passed = {} studentDTO passed = {}", id, studentDTO);
        return this.studentService.updateStudent(Long.valueOf(id), studentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudentById(@PathVariable String id) {
        log.info("StudentController::deleteStudentById -> id passed = {}", id);
        this.studentService.deleteStudentById(Long.valueOf(id));
    }

}