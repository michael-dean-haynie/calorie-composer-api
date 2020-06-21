package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class ComboFoodDTO {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String description;

    @Valid
    private List<ComboFoodFoodAmountDTO> foodAmounts = new ArrayList();

    @Valid
    private List<ComboFoodPortionDTO> portions = new ArrayList();

}
