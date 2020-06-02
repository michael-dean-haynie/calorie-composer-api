package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FDCFoodNutrient {
    private FDCNutrient nutrient;

    private BigDecimal amount;
}
