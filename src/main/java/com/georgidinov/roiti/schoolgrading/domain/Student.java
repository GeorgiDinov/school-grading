package com.georgidinov.roiti.schoolgrading.domain;

import com.georgidinov.roiti.schoolgrading.baseentity.BaseEntity;
import com.georgidinov.roiti.schoolgrading.baseentity.BaseNamedEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.CSV_HEADER_STUDENT_ID;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.CSV_HEADER_STUDENT_NAME;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_MAPPING_STUDENT;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_SCHOOL_CREDENTIALS_COLUMN_NAME_USER_CREDENTIALS_ID;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_STUDENT_COLUMN_NAME_STUDENT_ID;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_STUDENT_COLUMN_NAME_STUDENT_NAME;
import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ENTITY_STUDENT_TABLE_NAME;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"marks"})
@Entity
@Table(name = ENTITY_STUDENT_TABLE_NAME)
public class Student implements BaseEntity, BaseNamedEntity {


    @Id
    @Column(name = ENTITY_STUDENT_COLUMN_NAME_STUDENT_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = CSV_HEADER_STUDENT_ID)
    private Long id;

    @Column(name = ENTITY_STUDENT_COLUMN_NAME_STUDENT_NAME)
    @CsvBindByName(column = CSV_HEADER_STUDENT_NAME)
    private String name;

    @OneToMany(
            mappedBy = ENTITY_MAPPING_STUDENT,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Set<Mark> marks = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = ENTITY_SCHOOL_CREDENTIALS_COLUMN_NAME_USER_CREDENTIALS_ID)
    private SchoolUserCredentials credentials;

    //== public methods ==
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void addMark(Mark mark) {
        this.marks.add(mark);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;

        if (this.id.equals(student.getId())) {
            return true;
        }

        return this.name.equals(student.getName());

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}