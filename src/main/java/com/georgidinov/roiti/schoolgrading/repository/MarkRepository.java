package com.georgidinov.roiti.schoolgrading.repository;

import com.georgidinov.roiti.schoolgrading.domain.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    //the average mark a single student has in a single course
    @Query("SELECT AVG(mark) FROM Mark WHERE course.id = :courseId AND student.id = :studentId")
    Double avgMarkForStudentInSingleCourse(@Param("studentId") Long studentId,
                                           @Param("courseId") Long courseId);


    //the average mark a single student has across all courses
    @Query("SELECT AVG(mark) FROM Mark WHERE student.id = :studentId")
    Double avgMarkForStudentInAllCourses(@Param("studentId") Long studentId);


    //todo in progress 4, 27
    //the average mark all students were given in a single course
    @Query("SELECT AVG(mark) FROM Mark WHERE course.id = :courseId")
    Double avgMarkForACourse(@Param("courseId") Long courseId);


    //the average mark all students were given in all courses
    @Query("SELECT AVG(mark) FROM Mark")
    Double avgMarkForAllStudentsInAllCourses();


    //todo not implemented ---------------------------------------------------
    //the average mark for all existing combinations of a student and a course;
    //Double avgForAllExistingCombinationsForStudentAndCourse();


}