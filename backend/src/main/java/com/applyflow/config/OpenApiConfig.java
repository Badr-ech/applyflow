package com.applyflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI applyFlowOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("ApplyFlow API")
                .description("Event-driven job application tracking system")
                .version("1.0.0")
                .contact(new Contact()
                    .name("ApplyFlow Team")
                    .email("support@applyflow.com")))
            .servers(List.of(
                new Server().url("http://localhost:8080").description("Local Development"),
                new Server().url("http://localhost:8080").description("Production")
            ));
    }
}
