package com.example.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    // Serves Swagger at http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI dataOpenApi() {
        return new OpenAPI()
                .info(new Info().title("DataOpenApi")
                        .description("Api to upload, fetch, and delete data")
                        .version("1.0"));
    }
}
