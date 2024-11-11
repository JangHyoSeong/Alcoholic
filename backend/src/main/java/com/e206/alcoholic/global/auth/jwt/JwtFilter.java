package com.e206.alcoholic.global.auth.jwt;

import com.e206.alcoholic.domain.user.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    //    private final AuthService authService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request); // HTTP 요청에서 JWT 토큰 추출

            if (token != null) {
                if (isValidToken(token)) {
                    CustomUserDetails customUserDetails = CustomUserDetails.builder()
                            .userId(jwtUtil.getUserId(token))
                            .username(jwtUtil.getUsername(token))
                            .role("ROLE_" + jwtUtil.getRole(token))
                            .build();

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            customUserDetails,
                            null,
                            customUserDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("유효하지 않은 JWT 토큰");
                    return;
                }
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {  // 예외 처리
            log.error("사용자 인증을 설정할 수 없습니다", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("인증 실패: " + e.getMessage());
        }
    }

    // HTTP 요청 헤더에서 토큰을 추출하는 메서드
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        // Bearer로 시작하는 토큰인 경우 Bearer 제거 후 토큰 값만 반환
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    // 토큰의 유효성을 검사하는 메서드
    private boolean isValidToken(String token) {
        try {
            return !jwtUtil.isExpired(token);  // 토큰 만료 여부 확인
        } catch (Exception e) {
            log.error("토큰 검증 오류", e);
            return false;
        }
    }
}