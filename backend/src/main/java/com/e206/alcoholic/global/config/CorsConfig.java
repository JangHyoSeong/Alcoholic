package com.e206.alcoholic.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration // Spring의 설정 클래스
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        config.setAllowCredentials(true); // 인증 정보 허용 (쿠키, 인증 헤더 등)
        config.setExposedHeaders(List.of("Authorization")); // Authorization 헤더 노출
        config.setAllowedOrigins(List.of("http://localhost:5173")); // 허용할 프론트엔드 출처 등록
        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // 필요한 헤더만 명시적으로 허용
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드 설정
        source.registerCorsConfiguration("/**", config); // 모든 경로에 적용

        return new CorsFilter(source);
    }
}