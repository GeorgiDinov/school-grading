package com.georgidinov.roiti.schoolgrading.repository;

import com.georgidinov.roiti.schoolgrading.domain.SchoolUserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolUserCredentialsRepository extends JpaRepository<SchoolUserCredentials, Long> {

    Optional<SchoolUserCredentials> findByUsername(String username);

}