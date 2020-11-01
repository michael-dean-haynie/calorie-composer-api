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
@Table(name="combo_food_conversion_ratio")
public class ComboFoodConversionRatio {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="combo_food_id")
    private ComboFood comboFood;

    @DecimalMax("999.99")
    @Column(name="amount_a")
    private BigDecimal amountA;

    @Column(name="unit_a")
    private String unitA;

    @DecimalMax("999.99")
    @Column(name="amount_b")
    private BigDecimal amountB;

    @Column(name="unit_b")
    private String unitB;
}
