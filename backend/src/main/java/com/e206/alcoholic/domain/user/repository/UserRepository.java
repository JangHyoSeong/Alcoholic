package com.e206.alcoholic.domain.user.repository;

import com.e206.alcoholic.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username); // 사용자 아이디로 사용자 정보 조회
}