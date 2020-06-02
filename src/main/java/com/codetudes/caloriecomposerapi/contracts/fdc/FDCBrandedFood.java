package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FDCBrandedFood extends FDCFood{

    private String brand;

    private String ingredients;

    private BigDecimal servingSize;

    private String servingSizeUnit;

    private List<FDCFoodNutrient> nutrients;
}
