package com.e206.alcoholic.global.auth.repository;

import com.e206.alcoholic.global.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    Optional<Auth> findByUsername(String username); // 사용자 아이디로 인증 정보 조회
    boolean existsByUsername(String username); // 사용자 아이디 존재 여부 확인
}