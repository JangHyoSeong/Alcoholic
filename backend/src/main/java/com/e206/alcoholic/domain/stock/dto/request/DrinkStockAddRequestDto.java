package com.e206.alcoholic.domain.stock.dto.request;

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
    private String drinkName;
    private Integer position;
    private MultipartFile image;
}
