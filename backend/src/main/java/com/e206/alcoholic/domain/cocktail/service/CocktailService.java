package com.e206.alcoholic.domain.cocktail.service;

import com.e206.alcoholic.domain.category.entity.Category;
import com.e206.alcoholic.domain.category.repository.CategoryRepository;
import com.e206.alcoholic.domain.cocktail.dto.request.CocktailCreateRequestDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailDetailResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailListResponseDto;
import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import com.e206.alcoholic.domain.cocktail.mapper.CocktailMapper;
import com.e206.alcoholic.domain.cocktail.repository.CocktailRepository;
import com.e206.alcoholic.domain.ingredient.entity.Ingredient;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final CategoryRepository categoryRepository;

    // 전체 칵테일 목록 조회
    public List<CocktailListResponseDto> getAllCocktails() {
        return cocktailRepository.findAll().stream()
                .map(CocktailMapper::toCocktailListDto)
                .toList();
    }

    // 특정 칵테일의 상세 정보 조회
    public CocktailDetailResponseDto getCocktailDetail(Integer id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COCKTAIL_NOT_FOUND));
        return CocktailMapper.toCocktailDetailDto(cocktail);
    }

    // 칵테일 이름으로 검색
    public List<CocktailListResponseDto> searchCocktails(String name) {
        return cocktailRepository.findByNameContaining(name).stream()
                .map(CocktailMapper::toCocktailListDto)
                .toList();
    }

    @Transactional
    public CocktailDetailResponseDto createCocktail(CocktailCreateRequestDto requestDto) {
        // 카테고리 조회 및 재료 엔티티 생성
        List<Ingredient> ingredients = requestDto.getIngredients().stream()
                .map(ingredientDto -> {
                    Category category = categoryRepository.findById(ingredientDto.getCategoryId())
                            .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

                    return Ingredient.builder()
                            .categoryId(category)
                            .ingredient(ingredientDto.getIngredient())
                            .measure(ingredientDto.getMeasure())
                            .build();
                })
                .toList();

        // 칵테일 엔티티 생성
        Cocktail cocktail = new Cocktail(
                requestDto.getEnCocktailName(),
                requestDto.getKrCocktailName(),
                requestDto.getImage(),
                requestDto.getInstruction(),
                requestDto.getUserId(),
                new ArrayList<>()
        );

        // 양방향 연관관계 설정
        ingredients.forEach(ingredient -> ingredient.addCocktail(cocktail));

        // 저장 및 응답 반환
        Cocktail savedCocktail = cocktailRepository.save(cocktail);
        return CocktailMapper.toCocktailDetailDto(savedCocktail);
    }
}