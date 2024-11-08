package com.e206.alcoholic.domain.drink.repository;

import com.e206.alcoholic.domain.drink.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Optional<Drink> findDrinkByKrDrinkName(String name);
    List<Drink> findAllByCategoryId(Integer categoryId); // 카테고리ID로 술 목록 조회
}