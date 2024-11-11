package com.e206.alcoholic.domain.drink.repository;

import com.e206.alcoholic.domain.category.entity.Category;
import com.e206.alcoholic.domain.drink.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Integer> {
    Optional<Drink> findDrinkByKrDrinkName(String name);

    List<Drink> findAllByCategory(Category category);
}