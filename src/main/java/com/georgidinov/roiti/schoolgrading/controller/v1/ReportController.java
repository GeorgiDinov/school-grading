package com.georgidinov.roiti.schoolgrading.controller.v1;

import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.report.ReportDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.REPORT_BASE_URL;


@Slf4j
@RestController
@RequestMapping(REPORT_BASE_URL)
public class ReportController {

    //== fields ==
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/avg/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportDTO getAverageMarkForSingleCourse(@PathVariable String id,
                                                   @RequestBody CourseDTO courseDTO) throws EntityValidationException {
        log.info("ReportController::getAverageMarkForSingleCourse -> studentId passed = {}, courseDTO passed = {}", id, courseDTO);
        return this.reportService.avgMarkForStudentInSingleCourse(Long.valueOf(id), courseDTO);
    }


    @GetMapping("/avg/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportDTO getAverageMarkForAllCourses(@PathVariable String id) throws EntityValidationException {
        log.info("ReportController::getAverageMarkForAllCourses -> studentId passed = {}", id);
        return this.reportService.avgMarkForStudentInAllCourses(Long.valueOf(id));
    }

}