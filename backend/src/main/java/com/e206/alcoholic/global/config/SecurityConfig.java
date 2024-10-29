// SecurityConfig.java
package com.e206.alcoholic.global.config;

import com.e206.alcoholic.global.auth.jwt.JwtFilter;
import com.e206.alcoholic.global.auth.jwt.JwtUtil;
import com.e206.alcoholic.global.auth.service.AuthService;
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

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CorsConfig corsConfig;

    public SecurityConfig(@Lazy AuthService authService, JwtUtil jwtUtil, CorsConfig corsConfig) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.corsConfig = corsConfig;
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
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(jwtUtil, authService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}