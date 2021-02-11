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

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.CSV_HEADER_STUDENT_ID;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.CSV_HEADER_STUDENT_NAME;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_MAPPING_STUDENT;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_STUDENT_COLUMN_NAME_STUDENT_ID;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_STUDENT_COLUMN_NAME_STUDENT_NAME;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_STUDENT_TABLE_NAME;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"marks"})
@ToString(exclude = {"marks"})
@Entity
@Table(name = ENTITY_STUDENT_TABLE_NAME)
public class Student {

    @Id
    @Column(name = ENTITY_STUDENT_COLUMN_NAME_STUDENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = CSV_HEADER_STUDENT_ID)
    private Long id;

    @Column(name = ENTITY_STUDENT_COLUMN_NAME_STUDENT_NAME)
    @CsvBindByName(column = CSV_HEADER_STUDENT_NAME)
    private String studentName;

    @OneToMany(
            mappedBy = ENTITY_MAPPING_STUDENT,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Set<Mark> marks = new HashSet<>();

    //== public methods ==
    public void addMark(Mark mark) {
        this.marks.add(mark);
    }
}