package com.georgidinov.roiti.schoolgrading.api.v1.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MarkDTO {

    @Schema(required = true)
    private double mark;

    @Schema(nullable = true)
    private String date;

    @Schema(required = true)
    @JsonProperty("student_name")
    private String studentName;

    @Schema(required = true)
    @JsonProperty("course_name")
    private String courseName;

    @Schema(nullable = true)
    @JsonProperty("mark_url")
    private String markUrl;

}
