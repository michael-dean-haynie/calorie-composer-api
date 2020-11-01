package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * API Contract (DTO) for a ComboFood instance
 * API endpoint will accept and persist incomplete "draft" ComboFoods as well as fully validated "non-draft" ComboFoods
 */
@Data
public class ComboFoodDTO {
    private Long id;

    private ComboFoodDTO draftOf;

    @NotNull
    private Boolean isDraft;

    @Size(max = 100)
    private String description;

    @Valid
    private List<ComboFoodConversionRatioDTO> conversionRatios = new ArrayList();

    @Valid
    private List<ComboFoodConstituentDTO> constituents = new ArrayList();
}
