package com.e206.alcoholic.domain.stock.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrinkStockAddRequestDto {
    @NotEmpty(message = "술 이름 입력이 제대로 되었는지 확인해주세요")
    private String drinkName;
    @NotNull(message = "술의 위치는 null 일 수 없습니다.")
    private Integer position;
    private MultipartFile image;
}
