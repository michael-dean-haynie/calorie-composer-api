package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class PortionDTO {
    private Long id;

    @NotNull
    private Boolean isNutrientRefPortion = false;

    @NotNull
    private Boolean isServingSizePortion = false;

    @Size(max=10)
    private String metricUnit;

    @DecimalMax("999.99")
    private BigDecimal metricAmount;

    @Size(max=100)
    private String householdMeasure;

    @Size(max=45)
    private String householdUnit;

    @DecimalMax("999.99")
    private BigDecimal householdAmount;
}
