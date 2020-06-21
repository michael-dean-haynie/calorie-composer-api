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
@Table(name="combo_food_portion")
public class ComboFoodPortion {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="combo_food_id")
    private ComboFood comboFood;

    @NotBlank
    @Size(max=10)
    @Column(name="base_unit_name")
    private String baseUnitName;

    @NotNull
    @DecimalMax("999.99")
    @Column(name="base_unit_amount")
    private BigDecimal baseUnitAmount;

    @NotNull
    @Column(name="is_food_amount_ref_portion")
    private Boolean isFoodAmountRefPortion = false;

    @NotNull
    @Column(name="is_serving_size_portion")
    private Boolean isServingSizePortion = false;

    @Size(max=100)
    @Column(name="description")
    private String description;

    @Size(max=45)
    @Column(name="display_unit_name")
    private String displayUnitName;

    @DecimalMax("999.99")
    @Column(name="display_unit_amount")
    private BigDecimal displayUnitAmount;
}
