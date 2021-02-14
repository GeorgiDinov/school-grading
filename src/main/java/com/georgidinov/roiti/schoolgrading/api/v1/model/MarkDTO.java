package com.georgidinov.roiti.schoolgrading.api.v1.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MarkDTO {

    @NotNull
    private double mark;

    private String date;

    @NotNull
    @JsonProperty("student_name")
    private String studentName;

    @NotNull
    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("mark_url")
    private String markUrl;

}
