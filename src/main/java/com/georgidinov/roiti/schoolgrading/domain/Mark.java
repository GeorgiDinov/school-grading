package com.georgidinov.roiti.schoolgrading.domain;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
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
@ToString//(exclude = {"student", "course"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Mark")
@Table(name = "mark")
public class Mark {

    @Id
    @Column(name = "mark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "mark_id")
    private Long id;

    @Column(name = "mark")
    @CsvBindByName(column = "mark")
    private Double mark;

    @Column(name = "mark_date")
    @CsvBindByName(column = "mark_date")
    @CsvDate("yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime markDate;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;


    //== public methods ==
//    public void setCourse(Course course) {
//        this.course = course;
//        this.course.addMark(this);
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//        this.student.addMark(this);
//    }

}