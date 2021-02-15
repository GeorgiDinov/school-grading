package com.georgidinov.roiti.schoolgrading.controller.v1;

import com.georgidinov.roiti.schoolgrading.api.v1.report.ReportDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.REPORT_BASE_URL;


@Slf4j
@RestController
@RequestMapping(REPORT_BASE_URL)
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    //== fields ==
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/avg/student/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('avg:read')")
    public ReportDTO avgMarkForStudentInSingleCourse(@PathVariable String studentId,
                                                     @PathVariable String courseId) throws EntityValidationException {
        log.info("ReportController::avgMarkForStudentInSingleCourse -> studentId passed = {}, courseId passed = {}", studentId, courseId);
        return this.reportService.avgMarkForStudentInSingleCourse(Long.valueOf(studentId), Long.valueOf(courseId));
    }


    @GetMapping("/avg/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('avg:read')")
    public ReportDTO avgMarkForStudentInAllCourses(@PathVariable String studentId) throws EntityValidationException {
        log.info("ReportController::avgMarkForStudentInAllCourses -> studentId passed = {}", studentId);
        return this.reportService.avgMarkForStudentInAllCourses(Long.valueOf(studentId));
    }


    @GetMapping("/avg/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('mark:read')")
    public ReportDTO avgMarkForACourse(@PathVariable String courseId) throws EntityValidationException {
        log.info("ReportController::avgMarkForACourse -> courseId passed = {}", courseId);
        return this.reportService.avgMarkForACourse(Long.valueOf(courseId));
    }

    @GetMapping("/avg/student")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('mark:read')")
    public ReportDTO avgMarkForAllStudentsInAllCourses() {
        log.info("ReportController::avgMarkForAllStudentsInAllCourses");
        return this.reportService.avgMarkForAllStudentsInAllCourses();
    }


    //todo IMPLEMENTATION _________-------------_______________----------------_________
    @GetMapping("/avg")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('mark:read')")
    public ReportDTO avgForAllExistingCombinationsForStudentAndCourse() {
        log.info("ReportController::avgForAllExistingCombinationsForStudentAndCourse");
        return this.reportService.avgForAllExistingCombinationsForStudentAndCourse();
    }

}