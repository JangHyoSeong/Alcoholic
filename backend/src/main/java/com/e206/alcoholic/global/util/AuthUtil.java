package com.e206.alcoholic.global.util;

import com.e206.alcoholic.domain.user.dto.CustomUserDetails;
import com.e206.alcoholic.domain.user.entity.User;
import com.e206.alcoholic.domain.user.repository.UserRepository;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    public static CustomUserDetails getCustomUserDetails() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }

    @Transactional
    public User getUser() {
        CustomUserDetails customUserDetails = getCustomUserDetails();
        return userRepository.findById(customUserDetails.getUserId()).orElse(null);
    }

}
