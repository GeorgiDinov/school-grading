package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.StudentMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentListDTO;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.exception.EntityValidationException;
import com.georgidinov.roiti.schoolgrading.repository.StudentRepository;
import com.georgidinov.roiti.schoolgrading.validation.BaseNamedEntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_STUDENT_NOT_FOUND;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.STUDENT_BASE_URL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    BaseNamedEntityValidator baseNamedEntityValidator;

    StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.studentService = new StudentServiceImpl(this.studentRepository, StudentMapper.INSTANCE, baseNamedEntityValidator);
    }

    @Test
    void findAllStudents() {
        //given
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John Doe", new HashSet<>(), null));
        students.add(new Student(2L, "Anna Smith", new HashSet<>(), null));
        students.add(new Student(3L, "Linn Jones", new HashSet<>(),null));
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
        Student student = new Student(1L, "John Doe", new HashSet<>(), null);
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

    @Test
    void saveStudent() throws EntityValidationException {
        //given
        StudentDTO requestDTO = StudentDTO.builder().name("John Doe").build();
        StudentDTO expectedResponseDTO = StudentDTO.builder()
                .name("John Doe").studentUrl(STUDENT_BASE_URL + "/" + 1).build();
        Student student = new Student(1L, "John Doe", new HashSet<>(), null);
        when(this.studentRepository.save(any(Student.class))).thenReturn(student);

        //when
        StudentDTO responseDTO = this.studentService.saveStudent(requestDTO);

        //then
        assertNotNull(responseDTO);
        assertEquals(expectedResponseDTO.getName(), responseDTO.getName());
        assertEquals(expectedResponseDTO.getStudentUrl(), responseDTO.getStudentUrl());
    }

    @Test
    void updateStudent() throws EntityValidationException {
        //given
        Student student = new Student(1L, "John Doe", new HashSet<>(), null);
        StudentDTO requestDTO = StudentDTO.builder().name("John Doe Updated").build();
        StudentDTO expectedResponseDTO = StudentDTO.builder()
                .name("John Doe Updated").studentUrl(STUDENT_BASE_URL + "/" + 1).build();

        when(this.studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(this.studentRepository.save(any(Student.class))).thenReturn(student);

        //when
        StudentDTO responseDTO = this.studentService.updateStudent(1L, requestDTO);

        //then
        assertNotNull(responseDTO);
        assertEquals(expectedResponseDTO.getName(), responseDTO.getName());
        assertEquals(expectedResponseDTO.getStudentUrl(), responseDTO.getStudentUrl());
    }

    @Test
    void deleteStudentById() {
        when(this.studentRepository.existsById(anyLong())).thenReturn(true);
        this.studentService.deleteStudentById(anyLong());
        verify(studentRepository).deleteById(anyLong());
    }
}