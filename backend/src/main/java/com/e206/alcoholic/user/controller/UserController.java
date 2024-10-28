package com.e206.alcoholic.user.controller;

import com.e206.alcoholic.user.dto.NicknameRequestDto;
import com.e206.alcoholic.user.dto.SignUpRequestDto;
import com.e206.alcoholic.user.dto.UserResponseDto;
import com.e206.alcoholic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST API 컨트롤러
@RequestMapping("/api/v1") // 기본 URL 경로 설정
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class UserController {
    private final UserService userService;

    // 회원가입 엔드포인트
    @PostMapping("/auth/regist")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 사용자 정보 조회 엔드포인트
    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getCurrentUserInfo(userDetails.getUsername()));
    }

    // 사용자 닉네임 수정 엔드포인트
    @PatchMapping("/user")
    public ResponseEntity<UserResponseDto> updateNickname(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody NicknameRequestDto requestDto
    ) {
        return ResponseEntity.ok(userService.updateNickname(userDetails.getUsername(), requestDto));
    }
}