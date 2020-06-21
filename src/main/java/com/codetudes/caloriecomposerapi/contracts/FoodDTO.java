package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
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

    @Size(max = 500)
    private String ingredients;

    @DecimalMax("999.99")
    private BigDecimal servingSize;

    @Size(max=45)
    private String servingSizeUnit;

    @Size(max=100)
    private String householdServingFullText;

    @Valid
    private List<NutrientDTO> nutrients = new ArrayList();
}
