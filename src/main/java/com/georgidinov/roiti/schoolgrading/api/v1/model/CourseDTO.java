package com.georgidinov.roiti.schoolgrading.api.v1.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.georgidinov.roiti.schoolgrading.baseentity.BaseNamedEntity;
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
public class CourseDTO implements BaseNamedEntity {

    @Schema(required = true)
    @JsonProperty("course_name")
    private String name;


    @Schema(nullable = true)
    @JsonProperty("course_url")
    private String courseUrl;

    @Override
    public String getName() {
        return this.name;
    }
}