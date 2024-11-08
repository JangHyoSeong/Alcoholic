package com.e206.alcoholic.domain.category.entity;

import com.e206.alcoholic.domain.drink.entity.Drink;
import com.e206.alcoholic.domain.ingredient.entity.Ingredient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Drink> drinks = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    public void addIngredients(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}