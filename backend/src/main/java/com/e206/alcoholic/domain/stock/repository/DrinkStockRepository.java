package com.e206.alcoholic.domain.stock.repository;

import com.e206.alcoholic.domain.stock.entity.DrinkStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrinkStockRepository extends JpaRepository<DrinkStock, Integer> {
    List<DrinkStock> findByRefrigeratorId(Integer refrigeratorId);

    Optional<DrinkStock> findByRefrigeratorIdAndPosition(Integer refrigeratorId, Integer position);
}
