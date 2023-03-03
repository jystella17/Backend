package com.example.travelnode.oauth2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public GroupedOpenApi groupedOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("Routing API v1")
                .pathsToMatch(paths)
                .build();
    }
}
