package com.georgidinov.roiti.schoolgrading.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class UsernameAndPasswordAuthenticationRequest {

    @Schema(required = true)
    private String username;

    @Schema(required = true)
    private String password;

}