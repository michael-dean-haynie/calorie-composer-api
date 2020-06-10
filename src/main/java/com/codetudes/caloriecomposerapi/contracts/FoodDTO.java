package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FoodDTO {

    private String fdcId;

    private String description;

    private String brandOwner;

    private String ingredients;

    private BigDecimal servingSize;

    private String servingSizeUnit;

    private List<NutrientDTO> nutrients;

//    private List<FoodPortionDTO> foodPortions;
}
