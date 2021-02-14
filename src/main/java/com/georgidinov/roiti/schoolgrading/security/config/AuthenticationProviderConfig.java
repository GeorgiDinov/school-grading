package com.georgidinov.roiti.schoolgrading.security.config;

import com.georgidinov.roiti.schoolgrading.security.auth.SchoolUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationProviderConfig {

    //== fields ==
    private final PasswordEncoder passwordEncoder;
    private final SchoolUserDetailsService schoolUserDetailsService;

    //== constructors ==
    @Autowired
    public AuthenticationProviderConfig(PasswordEncoder passwordEncoder,
                                        SchoolUserDetailsService schoolUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.schoolUserDetailsService = schoolUserDetailsService;
    }

    //== bean methods ==
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(schoolUserDetailsService);
        return provider;
    }
}