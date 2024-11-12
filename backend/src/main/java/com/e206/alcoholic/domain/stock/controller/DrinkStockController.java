package com.e206.alcoholic.domain.stock.controller;

import com.e206.alcoholic.domain.refrigerator.service.RefrigeratorService;
import com.e206.alcoholic.domain.stock.dto.request.DrinkStockAddRequestDto;
import com.e206.alcoholic.domain.stock.dto.request.DrinkStockDeleteRequestDto;
import com.e206.alcoholic.domain.stock.dto.response.DrinkStockDetailsResponseDto;
import com.e206.alcoholic.domain.stock.dto.response.DrinkStockListResponseDto;
import com.e206.alcoholic.domain.stock.service.DrinkStockService;
import com.e206.alcoholic.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/refrigerators")
@RequiredArgsConstructor
public class DrinkStockController {
    private final RefrigeratorService refrigeratorService;
    private final DrinkStockService drinkStockService;

    @GetMapping("/{refrigeratorId}")
    public ResponseEntity<DrinkStockListResponseDto> getDrinkStockList(@PathVariable Integer refrigeratorId) {
        return ResponseEntity.ok(drinkStockService.getDrinkStock(refrigeratorId));
    }

    @PostMapping("/{refrigeratorId}")
    public ResponseEntity<CommonResponse> addDrinkStock(
            @PathVariable Integer refrigeratorId,
            @Valid @ModelAttribute DrinkStockAddRequestDto requestDto) {
        return ResponseEntity.ok(drinkStockService.addDrinkStock(refrigeratorId, requestDto));
    }

    @GetMapping("/stocks/{stockId}")
    public ResponseEntity<DrinkStockDetailsResponseDto> getDrinkStockDetails(@PathVariable Integer stockId) {
        return ResponseEntity.ok(drinkStockService.getDrinkStockDetails(stockId));
    }

    @DeleteMapping("/stocks/{stockId}")
    public ResponseEntity<CommonResponse> deleteDrinkStock(@PathVariable Integer stockId) {
        return ResponseEntity.ok(drinkStockService.deleteDrinkStock(stockId));
    }

    @PostMapping("/admin/{refrigeratorId}")
    public ResponseEntity<CommonResponse> adminAddDrinkStock(
            @PathVariable Integer refrigeratorId,
            @Valid @ModelAttribute DrinkStockAddRequestDto requestDto) {
        return ResponseEntity.ok(drinkStockService.adminAddDrinkStock(refrigeratorId, requestDto));
    }

    @DeleteMapping("/admin/stocks/{refrigeratorId}")
    public ResponseEntity<CommonResponse> adminDeleteDrinkStock(
            @PathVariable Integer refrigeratorId,
            @RequestBody DrinkStockDeleteRequestDto requestDto) {
        return ResponseEntity.ok(drinkStockService.adminDeleteDrinkStock(refrigeratorId, requestDto));
    }

}
