package com.e206.alcoholic.domain.ingredient.entity;

import com.e206.alcoholic.domain.category.Category;
import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 재료 정보를 저장하는 엔티티
@Entity
@Table(name = "ingredients")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // 칵테일과의 다대일 관계
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktailId; // 해당 재료가 사용되는 칵테일

    @ManyToOne(fetch = FetchType.LAZY) // 카테고리와의 다대일 관계
    @JoinColumn(name = "category_id")
    private Category categoryId; // 재료의 카테고리 분류

    private String ingredient; // 재료명
    private String measure; // 재료의 양
}