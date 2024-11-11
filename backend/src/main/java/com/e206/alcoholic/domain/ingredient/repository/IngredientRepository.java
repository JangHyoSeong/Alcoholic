package com.e206.alcoholic.domain.ingredient.repository;

import com.e206.alcoholic.domain.ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
