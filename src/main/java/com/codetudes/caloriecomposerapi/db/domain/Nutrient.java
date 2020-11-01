package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="nutrient")
public class Nutrient {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="food_id")
    private Food food;

    @NotBlank
    @Size(max=45)
    @Column(name="name")
    private String name;

    @Valid
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="unit_id")
    private Unit unit;

    @NotNull
    @DecimalMax("999.99")
    @Column(name="amount")
    private BigDecimal amount;
}
