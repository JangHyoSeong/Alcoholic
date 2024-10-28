// SecurityConfig.java
package com.e206.alcoholic.config;

import com.e206.alcoholic.user.jwt.JwtFilter;
import com.e206.alcoholic.user.jwt.JwtUtil;
import com.e206.alcoholic.user.jwt.LoginFilter;
import com.e206.alcoholic.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Lombok의 로깅 기능
@Configuration // Spring 설정 클래스
@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CorsConfig corsConfig;

    public SecurityConfig(@Lazy UserService userService, JwtUtil jwtUtil, CorsConfig corsConfig) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.corsConfig = corsConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authManager = authenticationConfiguration.getAuthenticationManager();

        http
                .addFilter(corsConfig.corsFilter()) // CORS 필터 적용
                .csrf().disable() // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll() // 인증 관련 경로는 모두 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 생성하지 않음
                .addFilter(new LoginFilter(authManager, jwtUtil)) // 로그인 처리를 담당
                .addFilterBefore(new JwtFilter(jwtUtil, userService), UsernamePasswordAuthenticationFilter.class); // JWT 토큰 검증 담당

        return http.build();
    }
}