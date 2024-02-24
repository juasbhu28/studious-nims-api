package com.nips.api.user.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${app.swagger.api.tittle}")
    private String tittle;

    @Value("${app.swagger.api.description}")
    private String description;

    @Value("${app.swagger.api.termsOfServiceUrl}")
    private String termsOfServiceUrl;

    @Value("${app.swagger.api.version}")
    private String version;

    @Value("${app.swagger.contact.name}")
    private String nameContact;

    @Value("${app.swagger.contact.url}")
    private String urlContact;

    @Value("${app.swagger.contact.email}")
    private String emailContact;

    @Value("${app.swagger.licence.name}")
    private String licenceName;

    @Value("${app.swagger.licence.url}")
    private String licenceUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(tittle)
                        .version(version)
                        .description(description)
                        .termsOfService(termsOfServiceUrl)
                        .contact(new Contact()
                                .name(nameContact)
                                .url(urlContact)
                                .email(emailContact))
                        .license(new License()
                                .name(licenceName)
                                .url(licenceUrl)));
    }

}

