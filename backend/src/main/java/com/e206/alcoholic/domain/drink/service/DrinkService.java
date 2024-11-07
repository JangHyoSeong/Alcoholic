package com.e206.alcoholic.domain.drink.service;

import com.e206.alcoholic.domain.drink.dto.DrinkDetailResponseDto;
import com.e206.alcoholic.domain.drink.dto.DrinkListResponseDto;
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
@Transactional(readOnly = true)  // 읽기 전용 트랜잭션 설정
public class DrinkService {
    private final DrinkRepository drinkRepository;

    // 전체 주류 목록 조회
    public List<DrinkListResponseDto> getDrinks() {
        return drinkRepository.findAll().stream()
                .map(DrinkListResponseDto::from)
                .collect(Collectors.toList());
    }

    // ID로 특정 주류 상세 정보 조회
    public DrinkDetailResponseDto getDrinkDetail(Long id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.DRINK_NOT_FOUND));
        return DrinkDetailResponseDto.from(drink);
    }
}