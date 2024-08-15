/*package com.project.local_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.project.local_management.controller"))
                .paths(PathSelectors.any()) // Scan all endpoints
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Local Management API",
                "Local Management System Documentation",
                "Version 1.0",
                "Terms of Service",
                new Contact("João Victor Silva França", "URL do Desenvolvedor", "joao.victor.franca07@aluno.ifce.edu.gov"),
                "API License",
                "URL da Licença",
                Collections.emptyList()
        );
    }
}
*/