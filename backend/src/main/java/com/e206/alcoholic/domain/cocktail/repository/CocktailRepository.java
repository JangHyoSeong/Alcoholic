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

    // 인기도순으로 칵테일 정렬
    @Query("SELECT c FROM Cocktail c ORDER BY c.value DESC")
    List<Cocktail> findAllOrderByValueDesc();

    // 사용자의 냉장고에 있는 주류 카테고리로 만들 수 있는 칵테일 검색
    @Query("""
    SELECT DISTINCT c FROM Cocktail c
    JOIN FETCH c.ingredients i
    JOIN i.category cat
    WHERE cat.id IN (
        SELECT d.category.id FROM DrinkStock ds
        JOIN ds.drink d
        JOIN ds.refrigerator r
        WHERE r.user.id = :userId
    )
    """)
    List<Cocktail> findCocktailsByUserDrinkStock(@Param("userId") Integer userId);
}