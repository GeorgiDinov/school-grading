package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.CourseMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;
import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import com.georgidinov.roiti.schoolgrading.validation.BaseNamedEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_COURSE_EXISTS;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_COURSE_NOT_FOUND;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    //== fields ==
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final BaseNamedEntityValidator baseNamedEntityValidator;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             CourseMapper courseMapper,
                             BaseNamedEntityValidator baseNamedEntityValidator) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.baseNamedEntityValidator = baseNamedEntityValidator;
    }

    @Override
    public CourseListDTO findAllCourses() {
        log.info("CourseServiceImpl::findAllCourses");
        return new CourseListDTO(
                this.courseRepository.findAll()
                        .stream()
                        .map(courseMapper::courseToCourseDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public CourseDTO findCourseById(Long id) {
        log.info("CourseServiceImpl::findCourseById -> id passed = {}", id);
        return this.courseRepository.findById(id)
                .map(courseMapper::courseToCourseDTO)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_COURSE_NOT_FOUND, id))
                );
    }

    @Override
    public CourseDTO saveCourse(CourseDTO courseDTO) throws EntityValidationException {
        log.info("CourseService::saveCourse -> course DTO passed  = {}", courseDTO);
        this.baseNamedEntityValidator.validate(courseDTO);
        if (isCourseWithNameExist(courseDTO)) {
            throw new EntityValidationException(
                    String.format(ERROR_COURSE_EXISTS, courseDTO.getName()));
        }
        return this.saveCourseToDatabase(this.courseMapper.courseDTOToCourse(courseDTO));
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) throws EntityValidationException {
        log.info("CourseService::updateCourse -> course DTO passed  = {}", courseDTO);
        this.baseNamedEntityValidator.validate(courseDTO);
        if (isCourseWithNameExist(courseDTO)) {
            throw new EntityValidationException(
                    String.format(ERROR_COURSE_EXISTS, courseDTO.getName()));
        }
        Course course = this.courseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException(
                        String.format(ERROR_COURSE_NOT_FOUND, id))
        );
        course.setName(courseDTO.getName());
        return this.saveCourseToDatabase(course);
    }

    @Override
    public void deleteCourseById(Long id) {
        log.info("CourseService::deleteCourseById -> id passed = {}", id);
        this.courseRepository.deleteById(id);
    }

    //todo implement patch

    //== private methods ==
    private CourseDTO saveCourseToDatabase(Course course) {
        Course savedCourse = this.courseRepository.save(course);
        return this.courseMapper.courseToCourseDTO(savedCourse);
    }

    private boolean isCourseWithNameExist(CourseDTO courseDTO) {
        Optional<Course> optionalCourse = this.courseRepository.findCourseByName(courseDTO.getName());
        return optionalCourse.isPresent();
    }
}