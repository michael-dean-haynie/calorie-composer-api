package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="combo_food_food_amount")
public class ComboFoodFoodAmount {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @NotNull
    @ManyToOne
    @JoinColumn(name="combo_food_id")
    private ComboFood comboFood;

    @NotNull
    @ManyToOne
    @JoinColumn(name="food_id")
    private Food food;

    @NotNull
    @DecimalMax("999.99")
    @Column(name="base_unit_amount")
    private BigDecimal baseUnitAmount;
}
