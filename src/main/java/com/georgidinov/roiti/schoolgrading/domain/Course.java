package com.georgidinov.roiti.schoolgrading.domain;

import com.opencsv.bean.CsvBindByName;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@EqualsAndHashCode(exclude = {"marks"})
@ToString(exclude = {"marks"})
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Course")
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "course_id")
    private Long id;

    @Column(name = "course_name")
    @CsvBindByName(column = "course_name")
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Mark> marks = new HashSet<>();

    //== public methods
    public void addMark(Mark mark) {
        this.marks.add(mark);
    }
}
