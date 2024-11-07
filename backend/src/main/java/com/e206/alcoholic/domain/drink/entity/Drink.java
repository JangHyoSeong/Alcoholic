package com.e206.alcoholic.domain.drink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "drinks")
@Getter
public class Drink {
    @Id
    private Integer id;  // 기본키
    private Integer categoryId;  // 카테고리 ID
    private String enDrinkName;  // 영문 주류명
    private String krDrinkName;  // 한글 주류명
    private Float alcoholDegree;  // 도수
    private String description;  // 설명
}
