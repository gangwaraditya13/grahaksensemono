
package com.adish.grahaksensemono.docs_config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class openApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Grahak Sense API")
                        .version("1.0.0")
                        .description(
                                "Grahak Sense API for authentication, products, " +
                                        "cart, sellers, users and administration."
                        )
                        .contact(new Contact()
                                .name("Aditya Kumar")
                                .email("gangwaraditya13@gmail.com")
                                .url("https://github.com/gangwaraditya13"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )
                .servers(List.of(
                        new Server()
                                .url("https://grahaksensemono.onrender.com")
                                .description("Production Server"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Server")
                ));
    }
}
