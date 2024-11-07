package com.e206.alcoholic.domain.refrigerator.entity;

import com.e206.alcoholic.domain.stock.entity.DrinkStock;
import com.e206.alcoholic.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "refrigerators")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refrigerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    private String name;

    @Column(nullable = false, unique = true)
    private String serialNumber;
    private Boolean isMain;

    @Builder.Default
    @OneToMany(mappedBy = "refrigerator", cascade = CascadeType.PERSIST)
    private List<DrinkStock> drinkStocks = new ArrayList<>();

    public static Refrigerator of(String name, String serialNumber, User user) {
        Refrigerator refrigerator = Refrigerator.builder()
                .name(name)
                .serialNumber(serialNumber)
                .build();
        if (user != null) {
            addUser(refrigerator, user);
        }
        return refrigerator;
    }

    private static void addUser(Refrigerator refrigerator, User user) {
        if (refrigerator.user == null) {
            refrigerator.user = user;
            user.addRefrigerator(refrigerator);
        }
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public void assignUser(User user) {
        this.user = user;
    }

    public void addDrinkStock(DrinkStock drinkStock) {
        drinkStocks.add(drinkStock);
    }
}