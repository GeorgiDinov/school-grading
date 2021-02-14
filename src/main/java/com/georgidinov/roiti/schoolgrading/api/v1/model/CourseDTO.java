package com.georgidinov.roiti.schoolgrading.api.v1.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.georgidinov.roiti.schoolgrading.baseentity.BaseNamedEntity;
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
public class CourseDTO implements BaseNamedEntity {

    @NotNull
    @JsonProperty("course_name")
    private String name;


    @JsonProperty("course_url")
    private String courseUrl;

    @Override
    public String getName() {
        return this.name;
    }
}