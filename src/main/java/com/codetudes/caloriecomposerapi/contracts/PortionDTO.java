package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class PortionDTO {
    private Long id;

    @NotBlank
    @Size(max=10)
    private String baseUnitName;

    @NotNull
    @DecimalMax("999.99")
    private BigDecimal baseUnitAmount;

    @NotNull
    private Boolean isNutrientRefPortion = false;

    @NotNull
    private Boolean isServingSizePortion = false;

    @Size(max=100)
    private String description;

    @Size(max=45)
    private String displayUnitName;

    @DecimalMax("999.99")
    private BigDecimal displayUnitAmount;
}
