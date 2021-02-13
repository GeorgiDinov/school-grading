package com.georgidinov.roiti.schoolgrading.repository;

import com.georgidinov.roiti.schoolgrading.domain.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    @Query("SELECT AVG(mark) FROM Mark WHERE course.name = :courseName AND student.id = :studentId")
    Double avgMarkForStudentInSingleCourse(@Param("studentId") Long studentId,
                                         @Param("courseName") String courseName);

}