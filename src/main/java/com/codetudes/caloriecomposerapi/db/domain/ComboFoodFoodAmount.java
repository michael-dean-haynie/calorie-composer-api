package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @ManyToOne
    @JoinColumn(name="food_id")
    private Food food;

    @Size(max=10)
    @Column(name="unit")
    private String unit;

    @DecimalMax("999.99")
    @Column(name="scalar")
    private BigDecimal scalar;
}
