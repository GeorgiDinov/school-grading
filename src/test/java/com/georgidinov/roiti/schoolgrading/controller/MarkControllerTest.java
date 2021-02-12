package com.georgidinov.roiti.schoolgrading.controller;

import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;
import com.georgidinov.roiti.schoolgrading.service.MarkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MarkControllerTest {

    @Mock
    MarkService markService;

    @InjectMocks
    MarkController markController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(markController).build();
    }

    @Test
    void findAllMarks() throws Exception {
        //given
        List<MarkDTO> marks = new ArrayList<>();
        marks.add(new MarkDTO(5.00d, LocalDateTime.now(), "Student Test Name", "Math"));
        marks.add(new MarkDTO(5.00d, LocalDateTime.now(), "Student Test Name1", "Literature"));
        marks.add(new MarkDTO(2.00d, LocalDateTime.now(), "Student Test Name2", "History"));
        when(this.markService.findAllMarks()).thenReturn(new MarkListDTO(marks));

        //when then
        mockMvc.perform(get("/api/v1/mark").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marks", hasSize(3)));
    }

    @Test
    void findMarkById() throws Exception {
        //given
        MarkDTO markDTO = new MarkDTO(5.00d, LocalDateTime.now(), "John Doe", "Math");
        when(this.markService.findMarkById(anyLong())).thenReturn(markDTO);

        //when then
        mockMvc.perform(get("/api/v1/mark/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark", equalTo(markDTO.getMark())))
                .andExpect(jsonPath("$.student_name", equalTo(markDTO.getStudentName())))
                .andExpect(jsonPath("$.course_name", equalTo(markDTO.getCourseName())));
    }
}