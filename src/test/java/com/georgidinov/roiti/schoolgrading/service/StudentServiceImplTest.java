package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.StudentMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentListDTO;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_STUDENT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.studentService = new StudentServiceImpl(this.studentRepository, StudentMapper.INSTANCE);
    }

    @Test
    void findAllStudents() {
        //given
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John Doe", new HashSet<>()));
        students.add(new Student(2L, "Anna Smith", new HashSet<>()));
        students.add(new Student(3L, "Linn Jones", new HashSet<>()));
        when(this.studentRepository.findAll()).thenReturn(students);

        //when
        StudentListDTO studentListDTO = this.studentService.findAllStudents();

        //then
        assertEquals(students.size(), studentListDTO.getStudents().size());
        verify(this.studentRepository).findAll();
    }

    @Test
    void findStudentById() {
        //given
        Student student = new Student(1L, "John Doe", new HashSet<>());
        when(this.studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //when
        StudentDTO studentDTO = this.studentService.findStudentById(1L);

        //then
        assertAll(
                "Perform assertions on dto object",
                () -> assertNotNull(studentDTO),
                () -> assertEquals(student.getName(), studentDTO.getName())
        );
        verify(this.studentRepository).findById(anyLong());
    }

    @Test
    void findStudentByIdNotFound() {
        //given
        Long id = 1L;
        when(this.studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(EntityNotFoundCustomException.class,
                () -> this.studentService.findStudentById(id)
        );

        //then
        String exceptionMessage = exception.getMessage();
        String expectedMessage = String.format(ERROR_STUDENT_NOT_FOUND, id);
        assertEquals(expectedMessage, exceptionMessage);
        verify(this.studentRepository).findById(anyLong());
    }
}