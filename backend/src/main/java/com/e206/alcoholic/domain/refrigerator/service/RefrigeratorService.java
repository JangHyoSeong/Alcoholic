package com.e206.alcoholic.domain.refrigerator.service;

import com.e206.alcoholic.domain.refrigerator.dto.request.RefrigeratorCreateRequestDto;
import com.e206.alcoholic.domain.refrigerator.dto.request.RefrigeratorUpdateRequestDto;
import com.e206.alcoholic.domain.refrigerator.dto.response.RefrigeratorListResponseDto;
import com.e206.alcoholic.domain.refrigerator.dto.response.RefrigeratorResponseDto;
import com.e206.alcoholic.domain.refrigerator.entity.Refrigerator;
import com.e206.alcoholic.domain.refrigerator.repository.RefrigeratorRepository;
import com.e206.alcoholic.domain.user.dto.CustomUserDetails;
import com.e206.alcoholic.domain.user.entity.User;
import com.e206.alcoholic.global.common.CommonResponse;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import com.e206.alcoholic.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefrigeratorService {
    private final RefrigeratorRepository refrigeratorRepository;
    private final AuthUtil authUtil;

    // 현재 로그인한 사용자의 모든 냉장고 목록을 조회
    public RefrigeratorListResponseDto getRefrigerators() {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        Integer currentUserId = customUserDetails.getUserId();

        List<RefrigeratorResponseDto> responses =
                refrigeratorRepository.findByUserId(currentUserId)
                        .stream()
                        .map(refrigerator -> RefrigeratorResponseDto.builder()
                                .id(refrigerator.getId())
                                .name(refrigerator.getName())
                                .isMain(refrigerator.getIsMain())
                                .build())
                        .toList();
        return RefrigeratorListResponseDto.builder()
                .results(responses)
                .build();
    }

    // 냉장고 생성 (이름, 유저 정보는 null)
    @Transactional
    public CommonResponse createRefrigerator(RefrigeratorCreateRequestDto requestDto) {
        if (refrigeratorRepository.existsBySerialNumber(requestDto.getSerialNumber()))
            throw new CustomException(ErrorCode.DUPLICATE_SERIAL_NUMBER);

        Refrigerator refrigerator = Refrigerator.of(null, requestDto.getSerialNumber(), null);
        refrigeratorRepository.save(refrigerator);
        return new CommonResponse("ok");
    }

    // 냉장고-유저 연결
    @Transactional
    public CommonResponse connectRefrigerator(RefrigeratorCreateRequestDto requestDto) {
        User user = authUtil.getUser();
        Refrigerator refrigerator = refrigeratorRepository.findBySerialNumber(requestDto.getSerialNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND));

        if (refrigerator.getUser() != null) throw new CustomException(ErrorCode.DUPLICATE_SERIAL_NUMBER);

        List<Refrigerator> userRefrigerators = refrigeratorRepository.findByUserId(user.getId());
        int arrLength = userRefrigerators.size();

        refrigerator.updateIsMain(arrLength == 0);
        refrigerator.updateName(String.format("술장고 %d", arrLength + 1));
        refrigerator.assignUser(user);

        return new CommonResponse("ok");
    }

    // 냉장고 삭제
    @Transactional
    public CommonResponse deleteRefrigerator(Integer refrigeratorId) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        Integer currentUserId = customUserDetails.getUserId();
        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND));

        // 본인 냉장고가 아니라면 에러
        if (!refrigerator.getUser().getId().equals(currentUserId)) {
            throw new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND);
        }

        // 메인 냉장고는 삭제 불가능
        if (Boolean.TRUE.equals(refrigerator.getIsMain())) {
            throw new CustomException(ErrorCode.MAIN_REFRIGERATOR_DELETE_DENIED);
        }

        refrigerator.updateIsMain(null);
        refrigerator.updateName(null);
        refrigerator.assignUser(null);

        return new CommonResponse("ok");
    }

    // 냉장고 이름 수정
    @Transactional
    public CommonResponse updateRefrigeratorName(int refrigeratorId, RefrigeratorUpdateRequestDto request) {
        CustomUserDetails customUserDetails = AuthUtil.getCustomUserDetails();
        Integer currentUserId = customUserDetails.getUserId();

        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND));
        if (!refrigerator.getUser().getId().equals(currentUserId)) {
            throw new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND);
        }

        refrigerator.updateName(request.getName());
        return new CommonResponse("ok");
    }

    @Transactional
    public Refrigerator getRefrigerator(Integer refrigeratorId, Integer userId) {
        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND));

        if (refrigerator.getUser() == null || !refrigerator.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND);
        }
        return refrigerator;
    }

}