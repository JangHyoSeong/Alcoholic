package com.e206.alcoholic.domain.drink.repository;

import com.e206.alcoholic.domain.category.entity.Category;
import com.e206.alcoholic.domain.drink.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Integer> {
    Optional<Drink> findDrinkByKrDrinkName(String name);

    @Query("SELECT d FROM Drink d WHERE d.krDrinkName = :name OR d.enDrinkName = :name")
    Optional<Drink> findDrinkByKrDrinkNameOrEnDrinkName(@Param("name") String name);

    List<Drink> findAllByCategory(Category category);
}