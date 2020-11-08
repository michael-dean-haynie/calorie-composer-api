package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FdcFoodNutrientDTO {

    private FdcNutrientDTO nutrient;

    // API seems inconsistent. May flop back to this?
//    private String name;
//
//    private String unitName;

    private BigDecimal amount;
}
