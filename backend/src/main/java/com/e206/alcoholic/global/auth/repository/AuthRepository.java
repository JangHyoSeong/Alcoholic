package com.e206.alcoholic.global.auth.repository;

import com.e206.alcoholic.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username); // 사용자 아이디로 인증 정보 조회
    boolean existsByUsername(String username); // 사용자 아이디 존재 여부 확인
}