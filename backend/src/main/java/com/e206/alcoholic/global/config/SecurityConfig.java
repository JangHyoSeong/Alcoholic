package com.e206.alcoholic.global.config;

import com.e206.alcoholic.global.auth.jwt.JwtFilter;
import com.e206.alcoholic.global.auth.jwt.JwtUtil;
import com.e206.alcoholic.global.auth.service.AuthService;
import com.e206.alcoholic.global.error.CustomAuthenticationEntryPoint;
import com.e206.alcoholic.global.error.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CorsConfig corsConfig;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(
            @Lazy AuthService authService,
            JwtUtil jwtUtil,
            CorsConfig corsConfig,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.corsConfig = corsConfig;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilter(corsConfig.corsFilter())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // 401 Unauthorized
                        .accessDeniedHandler(customAccessDeniedHandler)) // 403 Forbidden
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll() // Swagger UI 관련 설정
                        .requestMatchers("/api/v1/auth/**").permitAll() // 기존 인증 제외 API
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                .addFilterBefore(new JwtFilter(jwtUtil, authService),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}