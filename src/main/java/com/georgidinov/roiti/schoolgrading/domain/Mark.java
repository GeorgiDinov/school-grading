package com.georgidinov.roiti.schoolgrading.domain;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = {"student", "course"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mark")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "mark_id")
    private Long id;

    @CsvBindByName(column = "mark")
    private Double mark;

    @CsvBindByName(column = "mark_date")
    @CsvDate("yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime markDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @CsvRecurse
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @CsvRecurse
    private Course course;

}