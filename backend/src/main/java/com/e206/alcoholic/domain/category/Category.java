package com.e206.alcoholic.domain.category;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 재료 카테고리 정보를 저장하는 엔티티
@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    private Integer id; // 카테고리 식별자
    private String categoryName; // 카테고리 이름
}