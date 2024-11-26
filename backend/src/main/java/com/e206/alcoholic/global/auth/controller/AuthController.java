package com.e206.alcoholic.global.auth.controller;

import com.e206.alcoholic.global.auth.dto.request.SignUpRequestDto;
import com.e206.alcoholic.global.auth.dto.response.AuthResponseDto;
import com.e206.alcoholic.global.auth.dto.response.UsernameCheckResponseDto;
import com.e206.alcoholic.global.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/regist")
    public ResponseEntity<AuthResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        return ResponseEntity.ok(authService.signUp(requestDto));
    }

    @GetMapping("/check")
    public ResponseEntity<UsernameCheckResponseDto> checkDuplicateUsername(@RequestParam String username) {
        return ResponseEntity.ok(authService.checkUsernameDuplicated(username));
    }

    // 로그인은 /api/v1/auth/login으로 매핑됨
    // SecurityConfig.java를 참고

}