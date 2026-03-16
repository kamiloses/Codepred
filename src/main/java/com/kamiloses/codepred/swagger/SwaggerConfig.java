package com.kamiloses.codepred.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI taskApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management API")
                        .description("API for managing TODO tasks")
                        .version("1.0.0"));
    }
}