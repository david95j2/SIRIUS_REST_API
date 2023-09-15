package com.example.sirius_restapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MyConfiguration implements WebMvcConfigurer {
    private static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Value("${webgcs.cors.user.1}")
    private String user1Ip;

    @Value("${webgcs.cors.user.2}")
    private String user2Ip;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 요청 경로에 대해
                .allowedOrigins(user1Ip, user2Ip)
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","));  // 허용하려는 도메인을 설정
    }
}
