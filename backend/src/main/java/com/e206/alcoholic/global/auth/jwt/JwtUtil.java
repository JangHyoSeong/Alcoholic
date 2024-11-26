package com.e206.alcoholic.global.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    // 시크릿 키 초기화
    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor( // JWT 비밀키를 HMAC-SHA 키로 변환
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // JWT 토큰 생성
    public String createJwt(String username, Integer userId, String nickname, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("id", userId)
                .claim("nickname", nickname)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs))  // 만료 시간
                .signWith(secretKey) // 비밀키로 서명
                .compact(); // 토큰 생성
    }

    // 토큰에서 사용자 이름 추출
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public Integer getUserId(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Integer.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    // 토큰 만료 여부 확인
    public boolean isExpired(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
