package com.georgidinov.roiti.schoolgrading.api.v1.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MarkDTO {

    private double mark;
    private LocalDateTime date;
    private String studentName;
    private String courseName;

}
