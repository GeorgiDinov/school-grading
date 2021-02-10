package com.georgidinov.roiti.schoolgrading.domain;

import com.opencsv.bean.CsvBindByName;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@EqualsAndHashCode(exclude = {"students", "marks"})
@ToString(exclude = {"students", "marks"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @CsvBindByName(column = "course_id")
    private Long id;

    @Column(name = "course_name")
    @CsvBindByName(column = "course_name")
    private String name;

    @ManyToMany
    @JoinTable(name = "mark",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Mark> marks = new HashSet<>();

    //== public methods
    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void addMark(Mark mark) {
        this.marks.add(mark);
    }
}
