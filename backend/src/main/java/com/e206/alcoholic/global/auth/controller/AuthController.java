// AuthController.java
package com.e206.alcoholic.global.auth.controller;

import com.e206.alcoholic.global.auth.dto.request.LoginRequestDto;
import com.e206.alcoholic.global.auth.dto.request.SignUpRequestDto;
import com.e206.alcoholic.global.auth.dto.response.AuthResponseDto;
import com.e206.alcoholic.global.auth.jwt.JwtUtil;
import com.e206.alcoholic.global.auth.service.AuthService;
import com.e206.alcoholic.global.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // 회원가입 요청 처리
    @PostMapping("/regist")
    public ResponseEntity<AuthResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        return ResponseEntity.ok(authService.signUp(requestDto));
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestDto) {
        try {
            // 인증 처리
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
            );

            // JWT 토큰 생성
            String token = jwtUtil.createJwt(authentication.getName(), Duration.ofDays(30).toMillis());

            // 응답 객체 생성
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", authentication.getName());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(ErrorCode.USER_NOT_FOUND.getStatus())
                    .body(ErrorCode.USER_NOT_FOUND.getMessage());
        }
    }
}