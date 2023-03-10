package com.example.travelnode.oauth2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@OpenAPIDefinition(
        info = @Info(title = "Routing API Document",
                description = "여행 플래너 프로젝트 API 문서",
                version = "v1"
        )
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(tokenComponents()).info(new io.swagger.v3.oas.models.info.Info())
                .addSecurityItem(new SecurityRequirement().addList("Access Token"));
    }

    private Components tokenComponents() {
        SecurityScheme authorizationHeaderSchema = new SecurityScheme().name("Authorization")
                .type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER);

        return new Components().securitySchemes(Map.of("Access Token", authorizationHeaderSchema));
    }
}
