package com.e206.alcoholic.domain.cocktail.service;

import com.e206.alcoholic.domain.category.entity.Category;
import com.e206.alcoholic.domain.category.repository.CategoryRepository;
import com.e206.alcoholic.domain.cocktail.dto.request.CocktailCreateRequestDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailDetailResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailInventoryDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailInventoryResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailListResponseDto;
import com.e206.alcoholic.domain.cocktail.dto.response.CocktailResponseDto;
import com.e206.alcoholic.domain.cocktail.entity.Cocktail;
import com.e206.alcoholic.domain.cocktail.mapper.CocktailMapper;
import com.e206.alcoholic.domain.cocktail.repository.CocktailRepository;
import com.e206.alcoholic.domain.ingredient.entity.Ingredient;
import com.e206.alcoholic.domain.ingredient.repository.IngredientRepository;
import com.e206.alcoholic.domain.refrigerator.entity.Refrigerator;
import com.e206.alcoholic.domain.refrigerator.repository.RefrigeratorRepository;
import com.e206.alcoholic.domain.user.dto.CustomUserDetails;
import com.e206.alcoholic.domain.user.entity.User;
import com.e206.alcoholic.domain.user.repository.UserRepository;
import com.e206.alcoholic.global.S3bucket.S3ImageService;
import com.e206.alcoholic.global.common.CommonResponse;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import com.e206.alcoholic.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final S3ImageService s3ImageService;
    private final UserRepository userRepository;
    private final RefrigeratorRepository refrigeratorRepository;


    // 전체 칵테일 목록 조회
    public CocktailListResponseDto getAllCocktails() {
        List<CocktailResponseDto> cocktailResponseDtos = cocktailRepository.findAll().stream()
                .map(CocktailMapper::toCocktailListDto)
                .toList();
        return CocktailListResponseDto.builder()
                .result(cocktailResponseDtos)
                .build();
    }

    // 특정 칵테일의 상세 정보 조회
    public CocktailDetailResponseDto getCocktailDetail(Integer id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COCKTAIL_NOT_FOUND));
        return CocktailMapper.toCocktailDetailDto(cocktail);
    }

    // 칵테일 이름으로 검색
    public CocktailListResponseDto searchCocktails(String name) {
        List<CocktailResponseDto> cocktailResponseDtos = cocktailRepository.findByNameContaining(name).stream()
                .map(CocktailMapper::toCocktailListDto)
                .toList();
        return CocktailListResponseDto.builder()
                .result(cocktailResponseDtos)
                .build();
    }

    // 커스텀 칵테일 생성
    @Transactional
    public CommonResponse createCocktail(CocktailCreateRequestDto requestDto, MultipartFile image) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        User user = userRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 카테고리 조회 및 재료 엔티티 생성
        List<Ingredient> ingredients = requestDto.getIngredients().stream()
                .map(ingredientDto -> {
                    Category category = categoryRepository.findById(ingredientDto.getCategoryId())
                            .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
                    return Ingredient.builder()
                            .category(category)
                            .ingredientName(ingredientDto.getIngredient())
                            .measure(ingredientDto.getMeasure())
                            .build();
                })
                .toList();

        String imageUrl = s3ImageService.upload(image);

        Cocktail cocktail = Cocktail.builder()
                .enCocktailName(requestDto.getEnCocktailName())
                .krCocktailName(requestDto.getKrCocktailName())
                .image(imageUrl)
                .instruction(requestDto.getInstruction())
                .user(user)
                .build();

        ingredients.forEach(ingredient -> {
            ingredient.addCocktail(cocktail);
            ingredientRepository.save(ingredient);
        });
        cocktailRepository.save(cocktail);
        return new CommonResponse("created");
    }

    // 검색량 기반 칵테일 추천
    public CocktailListResponseDto getPopularCocktails() {
        List<CocktailResponseDto> cocktailResponseDtos = cocktailRepository.findAllOrderByValueDesc().stream()
                .map(CocktailMapper::toCocktailListDto)
                .toList();
        return CocktailListResponseDto.builder()
                .result(cocktailResponseDtos)
                .build();
    }

    // 재고 기반 칵테일 추천
    public CocktailInventoryResponseDto getStockBasedCocktails() {
        // 현재 로그인한 사용자 정보 가져오기
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        User user = userRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 사용자의 모든 냉장고 확인
        List<Refrigerator> refrigerators = refrigeratorRepository.findByUserId(user.getId());
        if (refrigerators.isEmpty()) {
            throw new CustomException(ErrorCode.REFRIGERATOR_DONT_HAVE);
        }

        // 사용자의 모든 냉장고의 재고를 기반으로 만들 수 있는 칵테일 찾기
        List<CocktailInventoryDto> cocktailInventoryResponseDtos = cocktailRepository  // 타입 수정
                .findCocktailsByUserDrinkStock(user.getId())
                .stream()
                .map(CocktailMapper::toCocktailInventoryDto)
                .toList();
        return CocktailInventoryResponseDto.builder()
                .result(cocktailInventoryResponseDtos)
                .build();
    }
}