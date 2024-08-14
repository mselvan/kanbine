package com.kanbine.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Swagger/OpenAPI documentation.
 * This class defines the OpenAPI bean that customizes the API documentation.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates and configures the OpenAPI instance for API documentation.
     *
     * @return an {@link OpenAPI} object with custom title, version, and description.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kanbine API")
                        .version("1.0")
                        .description("API documentation for Kanbine"));
    }
}
