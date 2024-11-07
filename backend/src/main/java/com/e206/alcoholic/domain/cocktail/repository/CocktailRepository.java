package com.e206.alcoholic.domain.cocktail.repository;

import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepository extends JpaRepository<Cocktail, Integer> { }
