package com.georgidinov.roiti.schoolgrading.repository;

import com.georgidinov.roiti.schoolgrading.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}