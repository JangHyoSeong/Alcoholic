package com.e206.alcoholic.domain.cocktail.repository;

import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CocktailRepository extends JpaRepository<Cocktail, Integer> {
    // 칵테일 이름으로 검색 (영문, 한글 동시 검색)
    @Query("SELECT c FROM Cocktail c WHERE c.enCocktailName LIKE %:name% OR c.krCocktailName LIKE %:name%")
    List<Cocktail> findByNameContaining(@Param("name") String name);
}