package com.georgidinov.roiti.schoolgrading.security.auth;

import com.georgidinov.roiti.schoolgrading.domain.SchoolUserCredentials;
import com.georgidinov.roiti.schoolgrading.repository.SchoolUserCredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class SchoolUserDetailsService implements UserDetailsService {

    private final SchoolUserCredentialsRepository SchoolUserCredentialsRepository;

    @Autowired
    public SchoolUserDetailsService(SchoolUserCredentialsRepository SchoolUserCredentialsRepository) {
        this.SchoolUserCredentialsRepository = SchoolUserCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("SchoolUserDetailsService::loadUserByUsername -> username passed = {}", username);
        Optional<SchoolUserCredentials> optionalSchoolUserCredentials =
                this.SchoolUserCredentialsRepository.findByUsername(username);

        SchoolUserCredentials userCredentials =
                optionalSchoolUserCredentials
                        .orElseThrow(() -> new UsernameNotFoundException(username + " NOT FOUND"));

        return new SchoolUserDetails(userCredentials);
    }

}