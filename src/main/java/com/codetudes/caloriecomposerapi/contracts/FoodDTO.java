package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class FoodDTO {
    private Long id;

    private Boolean isDraft;

    private String fdcId;

    private String description;

    private String brandOwner;

    private String ingredients;

    private UnitDTO ssrDisplayUnit;

    private UnitDTO csrDisplayUnit;

    @Valid
    private List<NutrientDTO> nutrients = new ArrayList();

    @Valid
    private List<ConversionRatioDTO> conversionRatios = new ArrayList();

    @Valid
    private FoodDTO draft;
}
