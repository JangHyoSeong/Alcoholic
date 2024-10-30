// RefrigeratorController.java
package com.e206.alcoholic.domain.refrigerator.controller;

import com.e206.alcoholic.domain.refrigerator.dto.request.CreateRequestDto;
import com.e206.alcoholic.domain.refrigerator.dto.response.ListResponseDto;
import com.e206.alcoholic.domain.refrigerator.dto.request.UpdateRequestDto;
import com.e206.alcoholic.domain.refrigerator.service.RefrigeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 냉장고 API를 처리하는 컨트롤러
@RestController
@RequestMapping("/api/v1/refrigerators")
@RequiredArgsConstructor
public class RefrigeratorController {
    // 냉장고 관련 비즈니스 로직을 처리하는 서비스 객체
    private final RefrigeratorService refrigeratorService;

    // 냉장고 조회 API
    @GetMapping
    public ResponseEntity<ListResponseDto> getRefrigerators() {
        return ResponseEntity.ok(refrigeratorService.getRefrigerators());
    }

    // 냉장고 등록 API
    @PostMapping
    public ResponseEntity<Void> createRefrigerator(@RequestBody CreateRequestDto request) {
        refrigeratorService.createRefrigerator(request);
        return ResponseEntity.ok().build();
    }

    // 냉장고 삭제 API
    @DeleteMapping("/{refrigeratorId}")
    public ResponseEntity<Void> deleteRefrigerator(@PathVariable Long refrigeratorId) {
        refrigeratorService.deleteRefrigerator(refrigeratorId);
        return ResponseEntity.ok().build();
    }

    // 냉장고 이름 수정 API
    @PatchMapping("/{refrigeratorId}")
    public ResponseEntity<Void> updateRefrigeratorName(
            @PathVariable Long refrigeratorId,
            @RequestBody UpdateRequestDto request) {
        refrigeratorService.updateRefrigeratorName(refrigeratorId, request);
        return ResponseEntity.ok().build();
    }
}