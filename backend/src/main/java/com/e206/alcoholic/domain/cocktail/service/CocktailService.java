package com.e206.alcoholic.domain.cocktail.service;

import com.e206.alcoholic.domain.cocktail.dto.CocktailDetailResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.CocktailListResponseDto;
import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import com.e206.alcoholic.domain.cocktail.mapper.CocktailMapper;
import com.e206.alcoholic.domain.cocktail.repository.CocktailRepository;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CocktailService {
    private final CocktailRepository cocktailRepository;

    // 전체 칵테일 목록 조회
    public List<CocktailListResponseDto> getAllCocktails() {
        return cocktailRepository.findAll().stream()
                .map(CocktailMapper::toCocktailListDto)
                .collect(Collectors.toList());
    }

    // 특정 칵테일의 상세 정보 조회
    public CocktailDetailResponseDto getCocktailDetail(Integer id) {
        // ID로 칵테일을 찾고, 없으면 예외 발생
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COCKTAIL_NOT_FOUND));
        return CocktailMapper.toCocktailDetailDto(cocktail);
    }
}