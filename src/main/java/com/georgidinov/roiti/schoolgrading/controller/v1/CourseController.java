package com.georgidinov.roiti.schoolgrading.controller.v1;

import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@SecurityRequirement(name = "bearerAuth")
public class CourseController {

    //== fields ==
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @Operation(summary = "This Operation Retrieves All Courses Stored In DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retrieve All Courses From DB",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "NOT Found",
                    content = @Content)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CourseListDTO findAllCourses() {
        log.info("CourseController::findAllCourses");
        return this.courseService.findAllCourses();
    }

    @Operation(summary = "This Operation Retrieves Single Course Based On The Course ID Value Passed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retrieve Particular Course From DB",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Error Message With Brief Description",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Error Message With Brief Description",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('course:read')")
    public CourseDTO findCourseById(@PathVariable String id) {
        log.info("CourseController::findCourseById -> id passed = {}", id);
        return this.courseService.findCourseById(Long.valueOf(id));
    }

    @Operation(summary = "This Operation Creates Single Course And Saves It In The Database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Saves Course In DB And Returns Name and Url For The New Record",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Error Message With Brief Description",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('course:write')")
    public CourseDTO saveCourse(@RequestBody CourseDTO courseDTO) throws EntityValidationException {
        log.info("CourseController::saveCourse -> courseDTO passed = {}", courseDTO);
        return this.courseService.saveCourse(courseDTO);
    }


    @Operation(summary = "This Operation Updates Course Based On The Course ID Value And The Sent Updating Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updates Course And Returns Name and Url For The Updated Record",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Error Message With Brief Description",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Error Message With Brief Description",
                    content = {@Content(mediaType = "application/json")})
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('course:write')")
    public CourseDTO updateCourse(@PathVariable String id,
                                  @RequestBody CourseDTO courseDTO) throws EntityValidationException {
        log.info("CourseController::updateCourse -> id passes = {} courseDTO passed = {}", id, courseDTO);
        return this.courseService.updateCourse(Long.valueOf(id), courseDTO);
    }


    @Operation(summary = "This Operation Deletes Course Based On The Course ID Value Passed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deletes Course From DB",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Error Message With Brief Description",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Error Message With Brief Description",
                    content = {@Content(mediaType = "application/json")})
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('course:write')")
    public void deleteCourseById(@PathVariable String id) {
        log.info("CourseController::deleteCourse -> id passed = {}", id);
        this.courseService.deleteCourseById(Long.valueOf(id));
    }

}