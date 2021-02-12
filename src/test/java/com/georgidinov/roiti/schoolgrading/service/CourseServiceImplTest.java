package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.CourseMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;
import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_COURSE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {

    @Mock
    CourseRepository courseRepository;

    CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseRepository, CourseMapper.INSTANCE);
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

}