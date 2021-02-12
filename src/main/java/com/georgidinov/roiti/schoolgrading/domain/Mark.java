package com.georgidinov.roiti.schoolgrading.domain;


import com.georgidinov.roiti.schoolgrading.baseentity.BaseEntity;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ENTITY_MARK_TABLE_NAME)
public class Mark implements BaseEntity {

    @Id
    @Column(name = ENTITY_MARK_COLUMN_NAME_MARK_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = CSV_HEADER_MARK_ID)
    private Long id;

    @Column(name = ENTITY_MARK_COLUMN_NAME_MARK_NAME)
    @CsvBindByName(column = CSV_HEADER_MARK_NAME)
    private Double mark;

    @Column(name = ENTITY_MARK_COLUMN_NAME_MARK_DATE)
    @CsvBindByName(column = CSV_HEADER_MARK_DATE)
    @CsvDate(ENTITY_MARK_DATE_TIME_FORMAT)
    private LocalDateTime markDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = ENTITY_COURSE_COLUMN_NAME_COURSE_ID)
    private Course course;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = ENTITY_STUDENT_COLUMN_NAME_STUDENT_ID)
    private Student student;

    //== public methods ==
    @Override
    public Long getId() {
        return this.id;
    }

    public void setCourse(Course course) {
        this.course = course;
        this.course.addMark(this);
    }

    public void setStudent(Student student) {
        this.student = student;
        this.student.addMark(this);
    }

}