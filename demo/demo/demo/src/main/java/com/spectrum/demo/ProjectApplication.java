package com.spectrum.demo;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	
	@Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("spectrum").consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE)).select().paths(regex("/*.*")).build();
    }

    /**
     * Sets the api's meta information as included in the json ResourceListing response.
     * 
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spectrum example").build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
}
