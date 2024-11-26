package com.e206.alcoholic.domain.drink.service;

import com.e206.alcoholic.domain.category.entity.Category;
import com.e206.alcoholic.domain.category.repository.CategoryRepository;
import com.e206.alcoholic.domain.drink.dto.DrinkDetailResponseDto;
import com.e206.alcoholic.domain.drink.dto.DrinkListResponseDto;
import com.e206.alcoholic.domain.drink.dto.DrinkResponseDto;
import com.e206.alcoholic.domain.drink.entity.Drink;
import com.e206.alcoholic.domain.drink.repository.DrinkRepository;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final CategoryRepository categoryRepository;

    // 주류 목록 조회
    public DrinkListResponseDto getDrinks() {
        List<DrinkResponseDto> drinkResponseDtos = drinkRepository.findAll().stream()
                .map(DrinkResponseDto::from)
                .collect(Collectors.toList());

        return DrinkListResponseDto.builder()
                .result(drinkResponseDtos)
                .build();
    }

    // ID로 특정 주류 상세 정보 조회
    public DrinkDetailResponseDto getDrinkDetail(Integer drinkId) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new CustomException(ErrorCode.DRINK_NOT_FOUND));
        return DrinkDetailResponseDto.from(drink);
    }

    // 카테고리별 술 목록 조회
    public DrinkListResponseDto getDrinksByCategory(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        List<DrinkResponseDto> drinkResponseDtos = drinkRepository.findAllByCategory(category).stream()
                .map(DrinkResponseDto::from)
                .collect(Collectors.toList());

        return DrinkListResponseDto.builder()
                .result(drinkResponseDtos)
                .build();
    }
}