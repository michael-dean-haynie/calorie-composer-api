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

    @Size(max = 500)
    private String ingredients;

    @Valid
    private List<NutrientDTO> nutrients = new ArrayList();

    @Valid
    private List<PortionDTO> portions = new ArrayList();
}
