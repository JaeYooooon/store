package com.zerobase.store.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    // Swagger Docket 빈 생성
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zerobase.store"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    // API 문서 정보 설정
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Store API")
                .description("매장 테이블 예약 서비스 구현")
                .build();
    }
}
