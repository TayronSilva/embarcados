package com.embarcados.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Embarcados API")
                        .version("1.0")
                        .description("Documentation API Embarcados"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name("Authorization") // Nome do cabeçalho que o Spring Security lê
                                .type(SecurityScheme.Type.APIKEY) // Evita o bug de caracteres
                                .in(SecurityScheme.In.HEADER)
                                .description("Digite: Bearer + espaço + seu token completo")));
    }
}