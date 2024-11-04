// AuthController.java
package com.e206.alcoholic.global.auth.controller;

import com.e206.alcoholic.global.auth.dto.request.LoginRequestDto;
import com.e206.alcoholic.global.auth.dto.request.SignUpRequestDto;
import com.e206.alcoholic.global.auth.dto.response.AuthResponseDto;
import com.e206.alcoholic.global.auth.dto.response.LoginResponseDto;
import com.e206.alcoholic.global.auth.jwt.JwtUtil;
import com.e206.alcoholic.global.auth.service.AuthService;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        try {
            // 인증 처리
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
            );

            // JWT 토큰 생성
            String token = jwtUtil.createJwt(authentication.getName(), Duration.ofDays(30).toMillis());

            // 헤더에 Authorization 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            // DTO 객체를 사용하여 응답 생성
            LoginResponseDto responseDto = new LoginResponseDto("로그인 성공");

            return ResponseEntity.ok().headers(headers).body(responseDto);

        } catch (AuthenticationException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

}