package com.georgidinov.roiti.schoolgrading.controller;

import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.StudentListDTO;
import com.georgidinov.roiti.schoolgrading.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest {

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentController studentController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(this.studentController).build();
    }

    @Test
    void findAllStudents() throws Exception {
        //given
        List<StudentDTO> students = new ArrayList<>();
        students.add(StudentDTO.builder().name("John Doe").build());
        students.add(StudentDTO.builder().name("Anna Smith").build());
        students.add(StudentDTO.builder().name("Jane Jones").build());
        when(this.studentService.findAllStudents()).thenReturn(new StudentListDTO(students));

        //when then
        mockMvc.perform(get("/api/v1/student").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(3)));
    }

    @Test
    void findStudentById() throws Exception {
        //given
        StudentDTO studentDTO = StudentDTO.builder().name("John Doe").build();
        when(this.studentService.findStudentById(anyLong())).thenReturn(studentDTO);

        //when then
        mockMvc.perform(get("/api/v1/student/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student_name", equalTo(studentDTO.getName())));
    }
}