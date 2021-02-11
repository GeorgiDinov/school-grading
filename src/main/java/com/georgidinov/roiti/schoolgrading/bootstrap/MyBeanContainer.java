package com.georgidinov.roiti.schoolgrading.bootstrap;

import com.georgidinov.roiti.schoolgrading.domain.Course;
import com.georgidinov.roiti.schoolgrading.domain.Mark;
import com.georgidinov.roiti.schoolgrading.domain.Student;
import com.opencsv.bean.CsvRecurse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyBeanContainer {

    @CsvRecurse
    private Student student;

    @CsvRecurse
    private Course course;

    @CsvRecurse
    private Mark mark;

}