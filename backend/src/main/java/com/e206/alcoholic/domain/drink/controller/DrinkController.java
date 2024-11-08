package com.e206.alcoholic.domain.drink.controller;

import com.e206.alcoholic.domain.drink.dto.DrinkDetailResponseDto;
import com.e206.alcoholic.domain.drink.dto.DrinkListResponseDto;
import com.e206.alcoholic.domain.drink.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 술 정보 관련 API 엔드포인트를 제공하는 컨트롤러
@RestController
@RequestMapping("/api/v1/drinks")
@RequiredArgsConstructor
public class DrinkController {
    private final DrinkService drinkService;

    // 전체 주류 목록 조회 API
    @GetMapping
    public List<DrinkListResponseDto> getDrinks() {
        return drinkService.getDrinks();
    }

    // 주류 상세 정보 조회 API
    @GetMapping("/{drinkId}")
    public DrinkDetailResponseDto getDrinkDetail(@PathVariable Long drinkId) {
        return drinkService.getDrinkDetail(drinkId);
    }

    // 카테고리별 술 목록 조회 API
    @GetMapping("/category/{categoryId}")
    public List<DrinkListResponseDto> getDrinksByCategory(@PathVariable Integer categoryId) {
        return drinkService.getDrinksByCategory(categoryId);
    }
}