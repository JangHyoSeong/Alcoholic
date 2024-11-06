package com.e206.alcoholic.domain.drinks.controller;

import com.e206.alcoholic.domain.drinks.dto.DrinkDetailResponseDto;
import com.e206.alcoholic.domain.drinks.dto.DrinkListResponseDto;
import com.e206.alcoholic.domain.drinks.service.DrinkService;
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
}