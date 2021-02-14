package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.report.ReportDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
import com.georgidinov.roiti.schoolgrading.validation.BaseNamedEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_COURSE_NOT_FOUND;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_STUDENT_NOT_FOUND;


@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    //== fields ==
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final MarkRepository markRepository;

    @Autowired
    public ReportServiceImpl(CourseRepository courseRepository,
                             StudentRepository studentRepository,
                             MarkRepository markRepository,
                             BaseNamedEntityValidator baseNamedEntityValidator) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.markRepository = markRepository;
    }

    @Override
    public ReportDTO avgMarkForStudentInSingleCourse(Long studentId, Long courseId) throws EntityValidationException {
        log.info("ReportService::avgMarkForStudentInSingleCourse -> studentId passed = {} courseId = {}", studentId, courseId);
        validateStudentId(studentId);
        validateCourseId(courseId);
        Double result = markRepository.avgMarkForStudentInSingleCourse(studentId, courseId);
        return reportBuilder(result);
    }

    @Override
    public ReportDTO avgMarkForStudentInAllCourses(Long studentId) throws EntityValidationException {
        log.info("ReportService::avgMarkForStudentInAllCourses -> studentId passed = {}", studentId);
        validateStudentId(studentId);
        Double result = this.markRepository.avgMarkForStudentInAllCourses(studentId);
        return reportBuilder(result);
    }

    @Override
    public ReportDTO avgMarkForACourse(Long courseId) throws EntityValidationException {
        log.info("ReportService::avgMarkForACourse");
        this.validateCourseId(courseId);
        return this.reportBuilder(this.markRepository.avgMarkForACourse(courseId));
    }

    @Override
    public ReportDTO avgMarkForAllStudentsInAllCourses() {
        log.info("ReportService::avgMarkForAllStudentsInAllCourses");
        return reportBuilder(this.markRepository.avgMarkForAllStudentsInAllCourses());
    }


    //todo IMPLEMENTATION --------------------------------------------------
    @Override
    public ReportDTO avgForAllExistingCombinationsForStudentAndCourse() {
        log.info("ReportService::avgForAllExistingCombinationsForStudentAndCourse");
        return new ReportDTO("THIS FUNCTIONALITY IS NOT IMPLEMENTED YET!");
    }

    //== private methods ==
    private void validateStudentId(Long studentId) throws EntityValidationException {
        if (!this.studentRepository.existsById(studentId)) {
            throw new EntityValidationException(String.format(ERROR_STUDENT_NOT_FOUND, studentId));
        }
    }

    private void validateCourseId(Long courseId) throws EntityValidationException {
        if (!this.courseRepository.existsById(courseId)) {
            throw new EntityValidationException(String.format(ERROR_COURSE_NOT_FOUND, courseId));
        }
    }

    private ReportDTO reportBuilder(Double result) {
        log.info("Result = {}", result);
        String formattedResult = result != null
                ? String.format("Average Result = %.2f", result)
                : "No Data Found";
        return new ReportDTO(formattedResult);
    }

}