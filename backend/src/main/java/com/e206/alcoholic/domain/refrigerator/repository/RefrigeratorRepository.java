package com.e206.alcoholic.domain.refrigerator.repository;

import com.e206.alcoholic.domain.refrigerator.entity.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
    List<Refrigerator> findByUserId(Long userId); // 냉장고 목록 조회
    Optional<Refrigerator> findByIdAndUserId(Long id, Long userId); // 특정 냉장고의 사용자인지 조회
    long countByUserId(Long userId); // 사용자별 냉장고 개수 조회
    boolean existsBySerialNumber(String serialNumber); // 시리얼 번호 중복 체크
}