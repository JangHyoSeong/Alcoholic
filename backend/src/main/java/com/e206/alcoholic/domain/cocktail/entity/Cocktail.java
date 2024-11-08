package com.e206.alcoholic.domain.cocktail.entity;

import com.e206.alcoholic.domain.ingredient.entity.Ingredient;
import com.e206.alcoholic.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String enCocktailName;
    private String krCocktailName;
    private String image;
    private String instruction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    // 새로운 칵테일을 생성하는 생성자
    public Cocktail(String enCocktailName, String krCocktailName, String image,
                    String instruction, User user, List<Ingredient> ingredients) {
        this.enCocktailName = enCocktailName;
        this.krCocktailName = krCocktailName;
        this.image = image;
        this.instruction = instruction;
        this.user = user;
        this.ingredients = ingredients;
    }

    public void addIngredients(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}