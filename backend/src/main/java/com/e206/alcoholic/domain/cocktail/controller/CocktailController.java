package com.e206.alcoholic.domain.cocktail.controller;

import com.e206.alcoholic.domain.cocktail.dto.response.CocktailDetailResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailListResponseDto;
import com.e206.alcoholic.domain.cocktail.service.CocktailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cocktails")
@RequiredArgsConstructor
public class CocktailController {
    private final CocktailService cocktailService;

    // 칵테일 목록 조회
    @GetMapping
    public ResponseEntity<List<CocktailListResponseDto>> getAllCocktails() {
        return ResponseEntity.ok(cocktailService.getAllCocktails());
    }

    // 칵테일 상세 조회
    @GetMapping("/{cocktailId}")
    public ResponseEntity<CocktailDetailResponseDto> getCocktailDetail(@PathVariable Integer cocktailId) {
        return ResponseEntity.ok(cocktailService.getCocktailDetail(cocktailId));
    }

    // 칵테일 검색
    @GetMapping("/search")
    public ResponseEntity<List<CocktailListResponseDto>> searchCocktails(@RequestParam String name) {
        return ResponseEntity.ok(cocktailService.searchCocktails(name));
    }
}