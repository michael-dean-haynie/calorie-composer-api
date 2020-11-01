package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class FoodDTO {
    private Long id;

    @Size(max=10)
    private String fdcId;

    @NotBlank
    @Size(max = 100)
    private String description;

    @Size(max = 100)
    private String brandOwner;

    @Size(max = 10000)
    private String ingredients;

    @Size(max=45)
    private String ssrDisplayUnit;

    @Size(max=45)
    private String csrDisplayUnit;

    @Valid
    private List<NutrientDTO> nutrients = new ArrayList();

    @Valid
    private List<ConversionRatioDTO> conversionRatios = new ArrayList();
}
