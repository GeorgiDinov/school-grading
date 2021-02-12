package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.CourseMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;
import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import com.georgidinov.roiti.schoolgrading.validation.BaseNamedEntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.COURSE_BASE_URL;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_COURSE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {

    @Mock
    CourseRepository courseRepository;

    @Mock
    BaseNamedEntityValidator baseNamedEntityValidator;

    CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseRepository, CourseMapper.INSTANCE, baseNamedEntityValidator);
    }

    @Test
    void findAllCourses() {
        //given
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1L, "Math", new HashSet<>()));
        courses.add(new Course(2L, "Literature", new HashSet<>()));
        courses.add(new Course(2L, "Literature", new HashSet<>()));
        when(this.courseRepository.findAll()).thenReturn(courses);

        //when
        CourseListDTO courseListDTO = this.courseService.findAllCourses();

        //then
        assertEquals(courses.size(), courseListDTO.getCourses().size());
        verify(courseRepository).findAll();
    }

    @Test
    void findCourseById() {
        //given
        Course course = new Course(1L, "Math", new HashSet<>());
        when(this.courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        //when
        CourseDTO courseDTO = this.courseService.findCourseById(1L);

        //then
        assertAll(
                "Performing assertions on course DTO object",
                () -> assertNotNull(courseDTO),
                () -> assertEquals(course.getName(), courseDTO.getName())
        );
        verify(this.courseRepository).findById(anyLong());
    }


    @Test
    void findCourseByIdNotFound() {
        //given
        Long id = 1L;
        when(this.courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(EntityNotFoundCustomException.class,
                () -> this.courseService.findCourseById(id)
        );

        //then
        String exceptionMessage = exception.getMessage();
        String expectedMessage = String.format(ERROR_COURSE_NOT_FOUND, id);
        assertEquals(expectedMessage, exceptionMessage);
        verify(this.courseRepository).findById(anyLong());
    }


    @Test
    void saveCourse() throws EntityValidationException {
        //given
        Long id = 1L;
        CourseDTO requestDTO = CourseDTO.builder().name("Computer Science").build();
        CourseDTO expectedResponseDTO = CourseDTO.builder().name("Computer Science").courseUrl(COURSE_BASE_URL + "/" + id).build();
        Course savedCourse = new Course(id, requestDTO.getName(), new HashSet<>());
        when(this.courseRepository.save(any(Course.class))).thenReturn(savedCourse);
        //when
        CourseDTO savedDTO = this.courseService.saveCourse(requestDTO);

        //then
        assertNotNull(savedDTO);
        assertEquals(expectedResponseDTO.getName(), savedDTO.getName());
        assertEquals(expectedResponseDTO.getCourseUrl(), savedDTO.getCourseUrl());
    }
}