package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ComboFoodFoodAmountDTO {
    private Long id;

    private FoodDTO food;

    @Size(max=10)
    private String unit;

    @DecimalMax("999.99")
    private BigDecimal amount;

}
