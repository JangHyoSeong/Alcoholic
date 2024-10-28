// UserService.java
package com.e206.alcoholic.user.service;

import com.e206.alcoholic.user.dto.NicknameRequestDto;
import com.e206.alcoholic.user.dto.SignUpRequestDto;
import com.e206.alcoholic.user.dto.UserResponseDto;
import com.e206.alcoholic.user.entity.User;
import com.e206.alcoholic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

// 인증 관련 비즈니스 로직을 처리하는 서비스 클래스
@Service // 서비스 계층임을 명시
@RequiredArgsConstructor // final 필드 생성자 자동 생성
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션 적용
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 정보 로드 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))) // 모든 사용자에게 "ROLE_USER" 권한 부여
                .build();
    }

    // 회원가입 처리 메서드
    @Transactional // 쓰기 작업이므로 트랜잭션 필요
    public UserResponseDto signUp(SignUpRequestDto requestDto) {
        // 아이디 중복 검사
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        // 사용자 생성 및 저장
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword())) // 비밀번호 암호화
                .nickname(requestDto.getNickname())
                .build();
        return new UserResponseDto(userRepository.save(user));
    }

    // 사용자 정보 조회
    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다: " + username));
    }

    // 현재 로그인한 사용자의 정보 조회
    public UserResponseDto getCurrentUserInfo(String username) {
        return new UserResponseDto(getUserByUsername(username));
    }

    // 사용자 닉네임 수정
    @Transactional
    public UserResponseDto updateNickname(String username, NicknameRequestDto requestDto) {
        User user = getUserByUsername(username);
        user.updateNickname(requestDto.getNickname());
        return new UserResponseDto(user);
    }
}