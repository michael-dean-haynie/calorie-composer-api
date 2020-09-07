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
@Table(name="conversion_ratio")
public class ConversionRatio {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="food_id")
    private Food food;

    @DecimalMax("999.99")
    @Column(name="amount_a")
    private BigDecimal amountA;

    @Column(name="unit_a")
    private String unitA;

    @Column(name="free_form_value_a")
    private String freeFormValueA;

    @DecimalMax("999.99")
    @Column(name="amount_b")
    private BigDecimal amountB;

    @Column(name="unit_b")
    private String unitB;

    @Column(name="free_form_value_b")
    private String freeFormValueB;
}
