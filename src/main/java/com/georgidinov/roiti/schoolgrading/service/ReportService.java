package com.georgidinov.roiti.schoolgrading.service;


import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.report.ReportDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;

public interface ReportService {

    ReportDTO avgMarkForStudentInSingleCourse(Long studentId, CourseDTO courseDTO) throws EntityValidationException;

    ReportDTO avgMarkForStudentInAllCourses(Long studentId) throws EntityValidationException;

}
