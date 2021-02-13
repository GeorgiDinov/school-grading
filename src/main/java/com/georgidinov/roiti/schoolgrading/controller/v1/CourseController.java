package com.georgidinov.roiti.schoolgrading.controller.v1;

import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.service.CourseService;
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

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.COURSE_BASE_URL;

@Slf4j
@RestController
@RequestMapping(COURSE_BASE_URL)
public class CourseController {

    //== fields ==
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CourseListDTO findAllCourses() {
        log.info("CourseController::findAllCourses");
        return this.courseService.findAllCourses();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO findCourseById(@PathVariable String id) {
        log.info("CourseController::findCourseById -> id passed = {}", id);
        return this.courseService.findCourseById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO saveCourse(@RequestBody CourseDTO courseDTO) throws EntityValidationException {
        log.info("CourseController::saveCourse -> courseDTO passed = {}", courseDTO);
        return this.courseService.saveCourse(courseDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseDTO updateCourse(@PathVariable String id,
                                  @RequestBody CourseDTO courseDTO) throws EntityValidationException {
        log.info("CourseController::updateCourse -> id passes = {} courseDTO passed = {}", id, courseDTO);
        return this.courseService.updateCourse(Long.valueOf(id), courseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourseById(@PathVariable String id) {
        log.info("CourseController::deleteCourse -> id passed = {}", id);
        this.courseService.deleteCourseById(Long.valueOf(id));
    }

}