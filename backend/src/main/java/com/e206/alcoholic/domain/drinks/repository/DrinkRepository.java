package com.e206.alcoholic.domain.drinks.repository;

import com.e206.alcoholic.domain.drinks.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 술 정보에 대한 데이터베이스 접근을 담당하는 레포지토리
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Optional<Drink> findDrinkByKrDrinkName(String name);
}