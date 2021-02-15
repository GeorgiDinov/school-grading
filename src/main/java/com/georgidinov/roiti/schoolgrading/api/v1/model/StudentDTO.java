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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StudentDTO implements BaseNamedEntity {

    @Schema(required = true)
    @JsonProperty("student_name")
    private String name;

    @Schema(nullable = true)
    @JsonProperty("student_url")
    private String studentUrl;

    @Override
    public String getName() {
        return this.name;
    }
}
