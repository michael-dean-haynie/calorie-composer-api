package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

@Data
public class FdcNutrientDTO {
    private String name;

    // This may be the culprit
//    private FdcNutrientUnitDTO nutrientUnit;

    private String unitName;
}
