package com.georgidinov.roiti.schoolgrading.security.auth;

import com.georgidinov.roiti.schoolgrading.domain.SchoolUserCredentials;
import com.georgidinov.roiti.schoolgrading.repository.SchoolUserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolUserDetailsService implements UserDetailsService {

    private final SchoolUserCredentialsRepository SchoolUserCredentialsRepository;

    @Autowired
    public SchoolUserDetailsService(SchoolUserCredentialsRepository SchoolUserCredentialsRepository) {
        this.SchoolUserCredentialsRepository = SchoolUserCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<SchoolUserCredentials> optionalSchoolUserCredentials =
                this.SchoolUserCredentialsRepository.findByUsername(username);

        SchoolUserCredentials userCredentials =
                optionalSchoolUserCredentials
                        .orElseThrow(() -> new UsernameNotFoundException(username + " NOT FOUND"));

        return new SchoolUserDetails(userCredentials);
    }

}