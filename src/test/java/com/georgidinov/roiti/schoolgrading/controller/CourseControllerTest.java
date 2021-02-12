package com.georgidinov.roiti.schoolgrading.controller;

import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.CourseListDTO;
import com.georgidinov.roiti.schoolgrading.service.CourseService;
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


class CourseControllerTest {

    @Mock
    CourseService courseService;

    @InjectMocks
    CourseController courseController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void findAllCourses() throws Exception {
        //given
        List<CourseDTO> courses = new ArrayList<>();
        courses.add(new CourseDTO("Math"));
        courses.add(new CourseDTO("Literature"));
        courses.add(new CourseDTO("History"));
        when(this.courseService.findAllCourses()).thenReturn(new CourseListDTO(courses));

        //when then
        mockMvc.perform(get("/api/v1/course").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courses", hasSize(3)));
    }


    @Test
    void findCourseById() throws Exception {
        //given
        CourseDTO courseDTO = new CourseDTO("Math");
        when(this.courseService.findCourseById(anyLong())).thenReturn(courseDTO);

        //when then
        mockMvc.perform(get("/api/v1/course/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(courseDTO.getName())));
    }
}