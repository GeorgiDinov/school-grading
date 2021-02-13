package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.report.ReportDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
import com.georgidinov.roiti.schoolgrading.validation.BaseNamedEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_STUDENT_NOT_FOUND;


@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    //== fields ==
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final MarkRepository markRepository;
    private final BaseNamedEntityValidator baseNamedEntityValidator;

    @Autowired
    public ReportServiceImpl(CourseRepository courseRepository,
                             StudentRepository studentRepository,
                             MarkRepository markRepository,
                             BaseNamedEntityValidator baseNamedEntityValidator) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.markRepository = markRepository;
        this.baseNamedEntityValidator = baseNamedEntityValidator;
    }

    @Override
    public ReportDTO avgMarkForStudentInSingleCourse(Long studentId, CourseDTO courseDTO) throws EntityValidationException {
        log.info("ReportService::avgMarkForStudentInSingleCourse -> studentId passed = {} courseDTO = {}", studentId, courseDTO);
        validateStudentId(studentId);
        validateCourseDTO(courseDTO);
        Double result = markRepository.avgMarkForStudentInSingleCourse(studentId, courseDTO.getName());
        log.info("Result = {}", result);
        String reportData = String.format("Average Mark For %s = %.2f", courseDTO.getName(), result);
        return new ReportDTO(reportData);
    }


    //== private methods ==
    private void validateCourseDTO(CourseDTO courseDTO) throws EntityValidationException {
        this.baseNamedEntityValidator.validate(courseDTO);
        this.courseRepository.findCourseByName(courseDTO.getName())
                .orElseThrow(() -> new RuntimeException("Course Not Found"));
    }

    private void validateStudentId(Long studentId) throws EntityValidationException {
        if (!this.studentRepository.existsById(studentId)) {
            throw new EntityValidationException(String.format(ERROR_STUDENT_NOT_FOUND, studentId));
        }
    }

}