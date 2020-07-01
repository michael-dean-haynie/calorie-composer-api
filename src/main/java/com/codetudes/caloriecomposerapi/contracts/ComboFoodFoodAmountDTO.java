package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

@Data
public class ComboFoodFoodAmountDTO {
    private Long id;

    private FoodDTO food;

    @DecimalMax("999.99")
    private BigDecimal metricAmount;

}
