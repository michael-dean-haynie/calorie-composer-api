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

    @NotNull
    private Boolean isDraft;

    @Size(max = 100)
    private String description;

    @Valid
    private List<ComboFoodFoodAmountDTO> foodAmounts = new ArrayList();

    @Valid
    private List<ComboFoodPortionDTO> portions = new ArrayList();
}
