// UserController.java
package com.e206.alcoholic.domain.user.controller;

import com.e206.alcoholic.domain.user.dto.request.NicknameRequestDto;
import com.e206.alcoholic.domain.user.dto.response.UserResponseDto;
import com.e206.alcoholic.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 정보 조회
    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUserInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }

    // 유저 닉네임 수정
    @PatchMapping("/user")
    public ResponseEntity<UserResponseDto> updateNickname(@RequestBody NicknameRequestDto requestDto) {
        return ResponseEntity.ok(userService.updateNickname(requestDto));
    }
}