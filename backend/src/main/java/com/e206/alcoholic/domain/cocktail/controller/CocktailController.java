package com.e206.alcoholic.domain.cocktail.controller;

import com.e206.alcoholic.domain.cocktail.dto.request.CocktailCreateRequestDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailDetailResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailInventoryResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailListResponseDto;
import com.e206.alcoholic.domain.cocktail.service.CocktailService;
import com.e206.alcoholic.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cocktails")
@RequiredArgsConstructor
public class CocktailController {
    private final CocktailService cocktailService;

    // 칵테일 목록 조회
    @GetMapping
    public ResponseEntity<CocktailListResponseDto> getAllCocktails() {
        return ResponseEntity.ok(cocktailService.getAllCocktails());
    }

    // 칵테일 상세 조회
    @GetMapping("/{cocktailId}")
    public ResponseEntity<CocktailDetailResponseDto> getCocktailDetail(@PathVariable Integer cocktailId) {
        return ResponseEntity.ok(cocktailService.getCocktailDetail(cocktailId));
    }

    // 칵테일 검색
    @GetMapping("/search")
    public ResponseEntity<CocktailListResponseDto> searchCocktails(@RequestParam String name) {
        return ResponseEntity.ok(cocktailService.searchCocktails(name));
    }

    // 커스텀 칵테일 등록
    @PostMapping
    public ResponseEntity<CommonResponse> createCocktail(
            @Valid @RequestPart(name = "cocktailData") CocktailCreateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(cocktailService.createCocktail(requestDto, image));
    }

    // 검색량 기준 칵테일 추천
    @GetMapping("/popularity")
    public ResponseEntity<CocktailListResponseDto> getPopularCocktails() {
        return ResponseEntity.ok(cocktailService.getPopularCocktails());
    }

    // 재고 기반 칵테일 추천
    @GetMapping("/stock")
    public ResponseEntity<CocktailInventoryResponseDto> getStockBasedCocktails() {  // 리턴 타입 수정
        return ResponseEntity.ok(cocktailService.getStockBasedCocktails());
    }
}