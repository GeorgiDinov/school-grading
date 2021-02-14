package com.georgidinov.roiti.schoolgrading.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(applicationInfo());
    }

    private Info applicationInfo() {

        Contact contact = new Contact()
                .name("Georgi Dinov")
                .url("https://github.com/GeorgiDinov/school-grading.git")
                .email("georgidinov1@abv.bg");

        Map<String, Object> licenceExtensions = new HashMap<>();
        licenceExtensions.put("https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

        License license = new License()
                .name("Apache Licence Version 2.0")
                .extensions(licenceExtensions);

        return new Info()
                .title("ROITI TASK")
                .description("Building Spring Boot Rest Api Related With Job Application")
                .contact(contact)
                .version("1.0.0")
                .license(license);
    }

}