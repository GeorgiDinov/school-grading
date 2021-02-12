package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.MarkMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;
import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_MARK_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class MarkServiceImplTest {

    @Mock
    MarkRepository markRepository;

    MarkService markService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.markService = new MarkServiceImpl(markRepository, MarkMapper.INSTANCE);
    }

    @Test
    void findAllMarks() {
        //given
        List<Mark> marks = new ArrayList<>();
        Course course = new Course(1L, "TestCourseOne", new HashSet<>());
        Student student = new Student(1L, "TestStudentOne", new HashSet<>());
        marks.add(new Mark(1L, 5.00d, LocalDateTime.now(), course, student));
        marks.add(new Mark(2L, 4.00d, LocalDateTime.now(), course, student));
        marks.add(new Mark(3L, 6.00d, LocalDateTime.now(), course, student));

        when(this.markRepository.findAll()).thenReturn(marks);

        //when
        MarkListDTO markListDTO = this.markService.findAllMarks();

        //then
        assertEquals(3, markListDTO.getMarks().size());
    }

    @Test
    void findMarkById() {
        //given
        Course course = new Course(1L, "TestCourseOne", new HashSet<>());
        Student student = new Student(1L, "TestStudentOne", new HashSet<>());
        Mark mark = new Mark(1L, 5.00d, LocalDateTime.now(), course, student);

        when(this.markRepository.findById(anyLong())).thenReturn(Optional.of(mark));

        //when
        MarkDTO markDTO = this.markService.findMarkById(1L);

        //then
        assertAll(
                "Performing multiple assertions on  markDTO object",
                () -> assertEquals(course.getName(), markDTO.getCourseName()),
                () -> assertEquals(student.getName(), markDTO.getStudentName()),
                () -> assertEquals(mark.getMark(), markDTO.getMark()),
                () -> assertEquals(mark.getMarkDate(), markDTO.getDate())
        );
    }

    @Test
    void findMarkByIdNotFound() {
        //given
        Long id = 1L;
        when(this.markRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(
                EntityNotFoundCustomException.class,
                () -> this.markService.findMarkById(id)
        );
        String exceptionMessage = exception.getMessage();
        String expectedMessage = String.format(ERROR_MARK_NOT_FOUND, id);
        //then
        assertEquals(expectedMessage, exceptionMessage);
        verify(this.markRepository).findById(anyLong());
    }
}