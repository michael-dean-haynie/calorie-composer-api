package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FdcBrandedFoodDTO extends FdcFoodDTO {
    private String brandOwner;

    private String ingredients;

    private BigDecimal servingSize;

    private String servingSizeUnit;

    private String householdServingFullText;

    private List<FdcFoodNutrientDTO> foodNutrients;
}
