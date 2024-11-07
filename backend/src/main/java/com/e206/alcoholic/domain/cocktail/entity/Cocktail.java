package com.e206.alcoholic.domain.cocktail.entity;

import com.e206.alcoholic.domain.ingredient.entity.Ingredient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// 칵테일 정보를 저장하는 엔티티
@Entity
@Table(name = "cocktails")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String enCocktailName;  // 칵테일 영문 이름
    private String krCocktailName;  // 칵테일 한글 이름
    private String image;           // 칵테일 이미지 URL
    private String instruction;     // 칵테일 제조 방법
    private Integer userId;         // 칵테일을 등록한 사용자 ID

    // 칵테일에 포함된 재료들과의 일대다 관계
    @OneToMany(mappedBy = "cocktailId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();
}