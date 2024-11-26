package com.e206.alcoholic.domain.user.service;

import com.e206.alcoholic.domain.user.dto.CustomUserDetails;
import com.e206.alcoholic.domain.user.dto.request.NicknameRequestDto;
import com.e206.alcoholic.domain.user.dto.response.UserResponseDto;
import com.e206.alcoholic.domain.user.entity.User;
import com.e206.alcoholic.domain.user.repository.UserRepository;
import com.e206.alcoholic.global.common.CommonResponse;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import com.e206.alcoholic.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    // 현재 사용자의 정보를 조회하는 메서드
    public UserResponseDto getUserInfo() {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        User user = userRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .build();
    }

    // 현재 사용자의 닉네임을 수정하는 메서드
    @Transactional
    public CommonResponse updateNickname(NicknameRequestDto requestDto) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        User user = userRepository.findByUsername(customUserDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateNickname(requestDto.getNickname());
        userRepository.save(user);

        return new CommonResponse("ok");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            return CustomUserDetails.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .nickname(user.getNickname())
                    .role(user.getRole().name())
                    .build();

        } catch (Exception e) {
            log.error("Error loading user details", e);
            throw e;
        }
    }
}