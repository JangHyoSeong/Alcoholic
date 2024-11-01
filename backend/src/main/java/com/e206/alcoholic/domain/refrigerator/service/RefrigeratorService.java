package com.e206.alcoholic.domain.refrigerator.service;

import com.e206.alcoholic.domain.refrigerator.dto.request.CreateRequestDto;
import com.e206.alcoholic.domain.refrigerator.dto.request.UpdateRequestDto;
import com.e206.alcoholic.domain.refrigerator.dto.response.ListResponseDto;
import com.e206.alcoholic.domain.refrigerator.entity.Refrigerator;
import com.e206.alcoholic.domain.refrigerator.repository.RefrigeratorRepository;
import com.e206.alcoholic.domain.user.service.UserService;
import com.e206.alcoholic.global.error.CustomException;
import com.e206.alcoholic.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // 읽기 전용 트랜잭션 설정 (성능 최적화)
public class RefrigeratorService {
    private final RefrigeratorRepository refrigeratorRepository;
    private final UserService userService;

    // 냉장고 조회 시 해당 사용자의 냉장고가 맞는지 확인하는 메서드
    private Refrigerator getRefrigeratorByIdAndUserId(int refrigeratorId, int userId) {
        return refrigeratorRepository.findByIdAndUserId(refrigeratorId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND));
    }

    // 현재 로그인한 사용자의 모든 냉장고 목록을 조회
    public ListResponseDto getRefrigerators() {
        int currentUserId = userService.getCurrentUserId();  // 현재 로그인한 사용자 ID 가져오기
        List<ListResponseDto.RefrigeratorResponse> responses =
                refrigeratorRepository.findByUserId(currentUserId)  // 해당 사용자의 냉장고 목록 조회
                        .stream()
                        .map(refrigerator -> ListResponseDto.RefrigeratorResponse.builder()  // 엔티티를 DTO로 변환
                                .id(refrigerator.getId())
                                .name(refrigerator.getName())
                                .isMain(refrigerator.isMain())
                                .build())
                        .toList();
        return ListResponseDto.builder()
                .results(responses)
                .build();
    }

    // 새로운 냉장고 등록
    @Transactional  // 쓰기 작업이므로 트랜잭션 필요
    public void createRefrigerator(CreateRequestDto request) {
        int currentUserId = userService.getCurrentUserId();
        // 시리얼 번호 중복 체크
        if (refrigeratorRepository.existsBySerialNumber(request.getSerialNumber())) {
            throw new CustomException(ErrorCode.DUPLICATE_SERIAL_NUMBER);
        }
        // 사용자의 냉장고 개수를 한 번만 조회
        int index = refrigeratorRepository.countByUserId(currentUserId);

        // 사용자의 첫 냉장고인 경우 메인 냉장고로 설정
        boolean isMain = index == 0;

        // 새 냉장고 엔티티 생성 및 저장
        Refrigerator refrigerator = Refrigerator.builder()
                .name("냉장고 " + (index + 1))
                .serialNumber(request.getSerialNumber())
                .userId(currentUserId)
                .isMain(isMain)
                .build();
        refrigeratorRepository.save(refrigerator);
    }

    // 냉장고 삭제
    @Transactional
    public void deleteRefrigerator(int refrigeratorId) {
        int currentUserId = userService.getCurrentUserId();
        Refrigerator refrigerator = getRefrigeratorByIdAndUserId(refrigeratorId, currentUserId);
        // 메인 냉장고는 삭제 불가능
        if (refrigerator.isMain()) {
            throw new CustomException(ErrorCode.MAIN_REFRIGERATOR_DELETE_DENIED);
        }
        refrigeratorRepository.delete(refrigerator);
    }

    // 냉장고 이름 수정
    @Transactional
    public void updateRefrigeratorName(int refrigeratorId, UpdateRequestDto request) {
        int currentUserId = userService.getCurrentUserId();
        Refrigerator refrigerator = getRefrigeratorByIdAndUserId(refrigeratorId, currentUserId);
        refrigerator.updateName(request.getName());
    }
}