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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"marks"})
@ToString(exclude = {"marks"})
@Builder
@Entity(name = "Student")
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "student_id")
    private Long id;

    @Column(name = "student_name")
    @CsvBindByName(column = "student_name")
    private String studentName;

    @OneToMany(mappedBy = "student")
    private Set<Mark> marks = new HashSet<>();

    //== public methods ==
    public void addMark(Mark mark) {
        mark.setStudent(this);
        this.marks.add(mark);
    }
}