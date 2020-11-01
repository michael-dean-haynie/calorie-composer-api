package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class NutrientDTO {
    private Long id;

    @Size(max=45)
    private String name;

    @Valid
    private UnitDTO unit;

    @DecimalMax("999.99")
    private BigDecimal amount;
}
