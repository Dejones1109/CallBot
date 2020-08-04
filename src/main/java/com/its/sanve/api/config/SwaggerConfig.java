package com.its.sanve.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2

public class SwaggerConfig {


    @Bean
    public Docket api() {

        Parameter headerParam = new ParameterBuilder()
                .name("x-access-token").defaultValue("").parameterType("header")
                .modelRef(new ModelRef("string")).description("Api key need to provide.").build();
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(Arrays.asList(headerParam))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
