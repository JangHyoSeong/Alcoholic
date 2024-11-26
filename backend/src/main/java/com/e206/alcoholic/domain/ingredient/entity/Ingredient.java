package com.e206.alcoholic.domain.ingredient.entity;

import com.e206.alcoholic.domain.category.entity.Category;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredients")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String ingredientName;
    private String measure;

    public void addCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
        if (!cocktail.getIngredients().contains(this)) {
            cocktail.addIngredients(this);
        }
    }

    public void addCategory(Category category) {
        this.category = category;
        if (!category.getIngredients().contains(this)) {
            category.addIngredients(this);
        }
    }
}