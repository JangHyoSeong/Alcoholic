package com.e206.alcoholic.domain.refrigerator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListResponseDto {
    private List<RefrigeratorResponse> results; // 조회된 냉장고 목록

    // 냉장고 개별 정보를 담는 내부 클래스
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefrigeratorResponse {
        private int id; // 냉장고 ID
        private String name; // 냉장고 이름
        private boolean isMain; // 메인 냉장고 여부
    }
}