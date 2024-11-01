package com.e206.alcoholic.domain.refrigerator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refrigerators")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refrigerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략 사용
    private int id; // 냉장고 ID

    @Column(nullable = false)
    private int userId; // 냉장고 소유자 ID

    @Column(nullable = false)
    private String name; // 냉장고 이름

    @Column(nullable = false)
    private String serialNumber; // 냉장고 시리얼 번호

    @Column(nullable = false)
    private boolean isMain; // 메인 냉장고 여부

    public void updateName(String name) {    // 냉장고 이름 업데이트 메소드
        this.name = name;
    }
}