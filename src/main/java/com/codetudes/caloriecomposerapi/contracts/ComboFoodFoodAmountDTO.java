package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ComboFoodFoodAmountDTO {
    private Long id;

    @NotNull
    private ComboFoodDTO comboFood;

    @NotNull
    private FoodDTO food;

    @NotNull
    @DecimalMax("999.99")
    private BigDecimal baseUnitAmount;

}
