package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="portion")
public class Portion {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="food_id")
    private Food food;

    @NotNull
    @Column(name="is_nutrient_ref_portion")
    private Boolean isNutrientRefPortion = false;

    @NotNull
    @Column(name="is_serving_size_portion")
    private Boolean isServingSizePortion = false;

    @NotBlank
    @Size(max=10)
    @Column(name="metric_unit")
    private String metricUnit;

    @NotNull
    @DecimalMax("999.99")
    @Column(name="metric_scalar")
    private BigDecimal metricScalar;

    @Size(max=100)
    @Column(name="household_measure")
    private String householdMeasure;

    @Size(max=45)
    @Column(name="household_unit")
    private String householdUnit;

    @DecimalMax("999.99")
    @Column(name="household_scalar")
    private BigDecimal householdScalar;
}
