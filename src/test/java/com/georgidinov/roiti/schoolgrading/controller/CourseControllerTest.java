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

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.COURSE_BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CourseControllerTest extends AbstractRestControllerTest {

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
        courses.add(CourseDTO.builder().name("Math").build());
        courses.add(CourseDTO.builder().name("Literature").build());
        courses.add(CourseDTO.builder().name("History").build());
        when(this.courseService.findAllCourses()).thenReturn(new CourseListDTO(courses));

        //when then
        mockMvc.perform(get("/api/v1/course").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courses", hasSize(3)));
    }


    @Test
    void findCourseById() throws Exception {
        //given
        CourseDTO courseDTO = CourseDTO.builder().name("Math").build();
        when(this.courseService.findCourseById(anyLong())).thenReturn(courseDTO);

        //when then
        mockMvc.perform(get("/api/v1/course/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course_name", equalTo(courseDTO.getName())));
    }

    @Test
    void saveCourse() throws Exception {
        //given
        long id = 1L;
        CourseDTO requestDTO = CourseDTO.builder().name("Computer Science").build();
        CourseDTO expectedResponseDTO = CourseDTO.builder().name("Computer Science").courseUrl(COURSE_BASE_URL + "/" + id).build();

        when(this.courseService.saveCourse(any(CourseDTO.class))).thenReturn(expectedResponseDTO);

        //when then
        mockMvc.perform(post(COURSE_BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.course_name", equalTo(expectedResponseDTO.getName())))
                .andExpect(jsonPath("$.course_url", equalTo(expectedResponseDTO.getCourseUrl())));
    }

    @Test
    void updateCourse() throws Exception {
        //given
        long id = 1L;
        CourseDTO requestDTO = CourseDTO.builder().name("Computer Science Updated").build();
        CourseDTO expectedResponseDTO = CourseDTO.builder().name("Computer Science Updated").courseUrl(COURSE_BASE_URL + "/" + id).build();

        when(this.courseService.updateCourse(anyLong(), any(CourseDTO.class))).thenReturn(expectedResponseDTO);

        //when then
        mockMvc.perform(put(COURSE_BASE_URL + "/" + id).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course_name", equalTo(expectedResponseDTO.getName())))
                .andExpect(jsonPath("$.course_url", equalTo(expectedResponseDTO.getCourseUrl())));
    }


    @Test
    void deleteCourseById() throws Exception {
        mockMvc.perform(delete(COURSE_BASE_URL + "/" + 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(this.courseService).deleteCourseById(anyLong());
    }
}