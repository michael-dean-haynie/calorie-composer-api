package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

@Data
public class FdcFoodNutrientDerivationDTO {
    private String code;

    private String description;

    private FdcFoodNutrientSourceDTO foodNutrientSource;
}
