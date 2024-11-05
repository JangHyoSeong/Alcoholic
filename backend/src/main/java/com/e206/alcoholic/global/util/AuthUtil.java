package com.e206.alcoholic.global.util;

import com.e206.alcoholic.domain.user.dto.CustomUserDetails;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static CustomUserDetails getCustomUserDetails() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }

}
