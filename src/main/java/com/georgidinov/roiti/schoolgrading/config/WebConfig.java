package com.georgidinov.roiti.schoolgrading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    //== bean methods ==
    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }

}
