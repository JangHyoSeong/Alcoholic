package com.e206.alcoholic.global.auth.service;

import com.e206.alcoholic.domain.user.entity.User;
import com.e206.alcoholic.global.auth.dto.SignUpDto;
import com.e206.alcoholic.global.auth.dto.request.SignUpRequestDto;
import com.e206.alcoholic.global.auth.dto.response.AuthResponseDto;
import com.e206.alcoholic.global.auth.dto.response.UsernameCheckResponseDto;
import com.e206.alcoholic.global.auth.repository.AuthRepository;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto signUp(SignUpRequestDto requestDto) {

        if (authRepository.existsByUsername(requestDto.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        SignUpDto signUpDto = SignUpDto.builder()
                .username(requestDto.getUsername())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .build();

        // 회원가입 진행
        User user = User.toUserFromSignUpRequestDto(signUpDto);
        return new AuthResponseDto(authRepository.save(user));
    }

    @Transactional
    public UsernameCheckResponseDto checkUsernameDuplicated(String username) {
        return UsernameCheckResponseDto.builder()
                .isDuplicated(authRepository.existsByUsername(username) ? "True" : "False")
                .build();
    }
}