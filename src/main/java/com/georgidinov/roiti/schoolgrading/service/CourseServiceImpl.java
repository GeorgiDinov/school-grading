package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.CourseMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_COURSE_NOT_FOUND;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    //== fields ==
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
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


}