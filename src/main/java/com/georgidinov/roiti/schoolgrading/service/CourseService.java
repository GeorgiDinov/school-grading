package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;

public interface CourseService {

    CourseListDTO findAllCourses();

    CourseDTO findCourseById(Long id);

    CourseDTO saveCourse(CourseDTO courseDTO) throws EntityValidationException;

    CourseDTO updateCourse(Long id, CourseDTO courseDTO) throws EntityValidationException;

    void deleteCourseById(Long id);

}