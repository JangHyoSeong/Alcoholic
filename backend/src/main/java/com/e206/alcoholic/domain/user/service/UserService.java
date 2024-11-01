// UserService.java
package com.e206.alcoholic.domain.user.service;

import com.e206.alcoholic.domain.user.dto.request.NicknameRequestDto;
import com.e206.alcoholic.domain.user.dto.response.UserResponseDto;
import com.e206.alcoholic.domain.user.entity.User;
import com.e206.alcoholic.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    // 현재 인증된 사용자의 아이디를 가져오는 메서드
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // 사용자 아이디로 사용자 정보를 조회하는 메서드
    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + username));
    }

    // 현재 사용자의 ID를 조회하는 메서드
    public int getCurrentUserId() {
        User user = getUserByUsername(getCurrentUsername());
        return user.getId();
    }

    // 현재 사용자의 정보를 조회하는 메서드
    public UserResponseDto getUserInfo() {
        User user = getUserByUsername(getCurrentUsername());
        return new UserResponseDto(user);
    }

    // 현재 사용자의 닉네임을 수정하는 메서드
    @Transactional
    public UserResponseDto updateNickname(NicknameRequestDto requestDto) {
        User user = getUserByUsername(getCurrentUsername());
        user.updateNickname(requestDto.getNickname());
        return new UserResponseDto(user);
    }
}