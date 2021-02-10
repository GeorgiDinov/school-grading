package com.georgidinov.roiti.schoolgrading.repository;

import com.georgidinov.roiti.schoolgrading.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}