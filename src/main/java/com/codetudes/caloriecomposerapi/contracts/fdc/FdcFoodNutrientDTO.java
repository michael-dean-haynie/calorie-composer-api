package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FdcFoodNutrientDTO {
    private FdcNutrientDTO nutrient;

    private BigDecimal amount;

//    private FdcFoodNutrientDerivationDTO foodNutrientDerivation;
}
