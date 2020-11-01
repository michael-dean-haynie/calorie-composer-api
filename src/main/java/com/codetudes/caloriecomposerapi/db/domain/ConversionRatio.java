package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
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

    @Valid
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="unit_a_id")
    private Unit unitA;

    @Column(name="free_form_value_a")
    private String freeFormValueA;

    @DecimalMax("999.99")
    @Column(name="amount_b")
    private BigDecimal amountB;

    @Valid
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="unit_b_id")
    private Unit unitB;

    @Column(name="free_form_value_b")
    private String freeFormValueB;
}
