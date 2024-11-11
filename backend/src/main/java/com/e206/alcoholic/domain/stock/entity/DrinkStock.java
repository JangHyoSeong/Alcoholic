package com.e206.alcoholic.domain.stock.entity;

import com.e206.alcoholic.domain.drink.entity.Drink;
import com.e206.alcoholic.domain.refrigerator.entity.Refrigerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "drink_stocks")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrinkStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drink_stock_id")
    private Integer id;

    private String image;

    @Column(name = "stock_time")
    private LocalDateTime stockTime;

    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refrigerator_id")
    private Refrigerator refrigerator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    private Drink drink;

    private String stockName;

    public static DrinkStock of(String image, Refrigerator refrigerator, Drink drink, Integer position, String stockName) {
        DrinkStock drinkStock = DrinkStock.builder()
                .image(image)
                .refrigerator(refrigerator)
                .drink(drink)
                .stockTime(LocalDateTime.now())
                .stockName(stockName)
                .position(position)
                .build();

        refrigerator.addDrinkStock(drinkStock);

        return drinkStock;
    }


}
