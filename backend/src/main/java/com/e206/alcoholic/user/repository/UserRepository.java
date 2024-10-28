package com.e206.alcoholic.user.repository;

import com.e206.alcoholic.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 사용자 리포지토리 인터페이스
@Repository // 데이터 접근 계층임을 표시
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username); // 사용자 아이디(username)로 사용자 정보(User) 조회
    boolean existsByUsername(String username); // 사용자 아이디(username) 존재 여부 확인
}