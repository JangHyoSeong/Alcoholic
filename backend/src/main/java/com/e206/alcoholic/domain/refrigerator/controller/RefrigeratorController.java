package com.e206.alcoholic.domain.refrigerator.controller;

import com.e206.alcoholic.domain.refrigerator.dto.request.RefrigeratorCreateRequestDto;
import com.e206.alcoholic.domain.refrigerator.dto.request.RefrigeratorUpdateRequestDto;
import com.e206.alcoholic.domain.refrigerator.dto.response.RefrigeratorListResponseDto;
import com.e206.alcoholic.domain.refrigerator.service.RefrigeratorService;
import com.e206.alcoholic.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/refrigerators")
@RequiredArgsConstructor
public class RefrigeratorController {
    private final RefrigeratorService refrigeratorService;

    @GetMapping
    public ResponseEntity<RefrigeratorListResponseDto> getRefrigerators() {
        return ResponseEntity.ok(refrigeratorService.getRefrigerators());
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createRefrigerator(@RequestBody RefrigeratorCreateRequestDto request) {
        return ResponseEntity.ok(refrigeratorService.createRefrigerator(request));
    }

    @PostMapping("/connect")
    public ResponseEntity<CommonResponse> connectRefrigerator(@RequestBody RefrigeratorCreateRequestDto request) {
        return ResponseEntity.ok(refrigeratorService.connectRefrigerator(request));
    }

    @DeleteMapping("/{refrigeratorId}")
    public ResponseEntity<CommonResponse> deleteRefrigerator(@PathVariable Integer refrigeratorId) {
        return ResponseEntity.ok(refrigeratorService.deleteRefrigerator(refrigeratorId));
    }

    @PatchMapping("/{refrigeratorId}")
    public ResponseEntity<CommonResponse> updateRefrigeratorName(
            @PathVariable int refrigeratorId,
            @RequestBody RefrigeratorUpdateRequestDto requestDto) {
        return ResponseEntity.ok(refrigeratorService.updateRefrigeratorName(refrigeratorId, requestDto));
    }
}