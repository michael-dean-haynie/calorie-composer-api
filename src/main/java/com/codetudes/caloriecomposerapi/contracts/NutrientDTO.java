package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import java.math.BigDecimal;

@Data
public class NutrientDTO {
    private Long id;

    private String name;

    @Valid
    private UnitDTO unit;

    private BigDecimal amount;
}
