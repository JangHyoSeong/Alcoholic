// AuthService.java
package com.e206.alcoholic.global.auth.service;

import com.e206.alcoholic.global.auth.dto.request.SignUpRequestDto;
import com.e206.alcoholic.global.auth.dto.response.AuthResponseDto;
import com.e206.alcoholic.global.auth.entity.Auth;
import com.e206.alcoholic.global.auth.repository.AuthRepository;
import com.e206.alcoholic.global.error.ErrorCode;
import com.e206.alcoholic.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 정보 조회
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        // 사용자 정보를 조회하고, 없으면 예외 발생
        Auth auth = authRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // User 객체 생성 및 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(auth.getUsername())
                .password(auth.getPassword())
                .build();
    }

    // 회원가입 처리
    @Transactional
    public AuthResponseDto signUp(SignUpRequestDto requestDto) {
        // 아이디 중복 체크를 먼저 수행
        if (authRepository.existsByUsername(requestDto.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        // 회원가입 진행
        Auth auth = Auth.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .build();

        return new AuthResponseDto(authRepository.save(auth));
    }
}