package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;

public interface CourseService {

    CourseListDTO findAllCourses();

    CourseDTO findCourseById(Long id);

}