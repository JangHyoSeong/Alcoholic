package com.e206.alcoholic.domain.stock.service;

import com.e206.alcoholic.domain.drink.entity.Drink;
import com.e206.alcoholic.domain.drink.repository.DrinkRepository;
import com.e206.alcoholic.domain.refrigerator.entity.Refrigerator;
import com.e206.alcoholic.domain.refrigerator.repository.RefrigeratorRepository;
import com.e206.alcoholic.domain.refrigerator.service.RefrigeratorService;
import com.e206.alcoholic.domain.stock.dto.request.DrinkStockAddRequestDto;
import com.e206.alcoholic.domain.stock.dto.request.DrinkStockDeleteRequestDto;
import com.e206.alcoholic.domain.stock.dto.response.DrinkStockDetailsResponseDto;
import com.e206.alcoholic.domain.stock.dto.response.DrinkStockListResponseDto;
import com.e206.alcoholic.domain.stock.dto.response.DrinkStockResponseDto;
import com.e206.alcoholic.domain.stock.entity.DrinkStock;
import com.e206.alcoholic.domain.stock.repository.DrinkStockRepository;
import com.e206.alcoholic.domain.user.dto.CustomUserDetails;
import com.e206.alcoholic.global.S3bucket.S3ImageService;
import com.e206.alcoholic.global.common.CommonResponse;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import com.e206.alcoholic.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrinkStockService {
    private final DrinkStockRepository drinkStockRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final DrinkRepository drinkRepository;
    private final RefrigeratorService refrigeratorService;
    private final S3ImageService s3ImageService;


    // 냉장고에 따른 재고 목록을 리턴
    @Transactional
    public DrinkStockListResponseDto getDrinkStock(Integer refrigeratorId) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        Integer currentUserId = customUserDetails.getUserId();

        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND));

        if (customUserDetails.getRole().equals("ROLE_BOARD")) {
        } else if (refrigerator.getUser() == null || !refrigerator.getUser().getId().equals(currentUserId)) {
            throw new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND);
        }

        List<DrinkStock> drinkStockList = drinkStockRepository.findByRefrigeratorId(refrigeratorId);
        List<DrinkStockResponseDto> result = new ArrayList<>();

        drinkStockList.forEach(drinkStock -> {
            Drink drink = drinkStock.getDrink();

            DrinkStockResponseDto.DrinkStockResponseDtoBuilder dtoBuilder = DrinkStockResponseDto.builder()
                    .id(drinkStock.getId())
                    .stockTime(drinkStock.getStockTime())
                    .position(drinkStock.getPosition())
                    .imageUrl(drinkStock.getImage());

            if (drink != null) {
                dtoBuilder.name(drink.getEnDrinkName())
                        .koreanName(drink.getKrDrinkName());
            } else {
                dtoBuilder.koreanName(drinkStock.getStockName());
            }

            result.add(dtoBuilder.build());
        });
        return new DrinkStockListResponseDto(result);
    }

    // 냉장고 술 재고 추가
    @Transactional
    public CommonResponse addDrinkStock(Integer refrigeratorId, DrinkStockAddRequestDto requestDto) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        Refrigerator refrigerator = refrigeratorService.getRefrigerator(refrigeratorId, customUserDetails.getUserId());

        // krDrinkName 또는 enDrinkName으로 검색
        Drink drink = drinkRepository
                .findDrinkByKrDrinkNameOrEnDrinkName(requestDto.getDrinkName())
                .orElse(null); 

        Integer position = requestDto.getPosition();

        drinkStockRepository.findByRefrigeratorIdAndPosition(refrigeratorId, position).ifPresent(existingStock -> {
            throw new CustomException(ErrorCode.ALREADY_IN_POSITION);
        });

        String imageURL = requestDto.getImage() != null ? s3ImageService.upload(requestDto.getImage()) : null;
        DrinkStock drinkStock = DrinkStock.of(imageURL, refrigerator, drink, position, requestDto.getDrinkName());
        drinkStockRepository.save(drinkStock);
        return new CommonResponse("ok");
    }

    // 술 재고 상세 정보
    public DrinkStockDetailsResponseDto getDrinkStockDetails(Integer drinkStockId) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        List<Refrigerator> refrigerators = refrigeratorRepository.findByUserId(customUserDetails.getUserId());
        DrinkStock drinkStock = drinkStockRepository.findById(drinkStockId).orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));

        Drink drink = drinkStock.getDrink();
        if (drink == null) {
            throw new CustomException(ErrorCode.DRINK_NOT_FOUND);
        }

        if (!refrigerators.contains(drinkStock.getRefrigerator())) {
            throw new CustomException(ErrorCode.STOCK_NOT_IN_USER_REFRIGERATORS);
        }
        return DrinkStockDetailsResponseDto.builder()
                .stockId(drinkStockId)
                .name(drink.getEnDrinkName())
                .koreanName(drink.getKrDrinkName())
                .degree(drink.getAlcoholDegree())
                .stockTime(drinkStock.getStockTime())
                .position(drinkStock.getPosition())
                .type(drinkStock.getDrink().getCategory().getCategoryName())
                .build();
    }

    // 술 재고 삭제
    @Transactional
    public CommonResponse deleteDrinkStock(Integer drinkStockId) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        List<Refrigerator> refrigerators = refrigeratorRepository.findByUserId(customUserDetails.getUserId());
        DrinkStock drinkStock = drinkStockRepository.findById(drinkStockId).orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));

        if (!refrigerators.contains(drinkStock.getRefrigerator())) {
            throw new CustomException(ErrorCode.STOCK_NOT_IN_USER_REFRIGERATORS);
        }
        drinkStockRepository.delete(drinkStock);
        return new CommonResponse("deleted");
    }


    @Transactional
    public CommonResponse adminAddDrinkStock(Integer refrigeratorId, DrinkStockAddRequestDto requestDto) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        if (!customUserDetails.getRole().equals("ROLE_BOARD")) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND));

        Drink drink = drinkRepository
                .findDrinkByKrDrinkNameOrEnDrinkName(requestDto.getDrinkName())
                .orElseThrow(() -> new CustomException(ErrorCode.DRINK_NOT_FOUND));

        Integer position = requestDto.getPosition();

        drinkStockRepository.findByRefrigeratorIdAndPosition(refrigeratorId, position).ifPresent(existingStock -> {
            throw new CustomException(ErrorCode.ALREADY_IN_POSITION);
        });

        String imageURL = s3ImageService.upload(requestDto.getImage());
        DrinkStock drinkStock = DrinkStock.of(imageURL, refrigerator, drink, position, requestDto.getDrinkName());

        drinkStockRepository.save(drinkStock);
        return new CommonResponse("ok");
    }


    @Transactional
    public CommonResponse adminDeleteDrinkStock(Integer refrigeratorId, DrinkStockDeleteRequestDto requestDto) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        if (!customUserDetails.getRole().equals("ROLE_BOARD")) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        DrinkStock drinkStock = drinkStockRepository.findByRefrigeratorIdAndPosition(refrigeratorId, requestDto.getPosition())
                .orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));
        drinkStockRepository.delete(drinkStock);
        return new CommonResponse("deleted");
    }
}
