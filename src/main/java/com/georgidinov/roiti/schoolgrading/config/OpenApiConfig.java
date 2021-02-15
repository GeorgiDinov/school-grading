package com.georgidinov.roiti.schoolgrading.config;

import com.georgidinov.roiti.schoolgrading.security.jwt.UsernameAndPasswordAuthenticationRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;


@OpenAPIDefinition
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {


    private UsernameAndPasswordAuthenticationRequest authenticationRequest;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .paths(new Paths().addPathItem("/login", new PathItem().post(this.operation())))
                .info(applicationInfo());
    }


    private List<Parameter> parameters() {
        Parameter username = new Parameter()
                .name("username")
                .schema(new StringSchema().name("username"));
        Parameter password = new Parameter()
                .name("password")
                .schema(new StringSchema().name("password"));

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(username);
        parameters.add(password);
        return parameters;
    }

    private Operation operation() {
        Operation operation = new Operation();
        operation.requestBody(new RequestBody()
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new ObjectSchema()
                                .example(authenticationRequest)
                                .addProperties("username", new StringSchema())
                                .addProperties("password", new StringSchema())
                        ))));
        return operation;
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