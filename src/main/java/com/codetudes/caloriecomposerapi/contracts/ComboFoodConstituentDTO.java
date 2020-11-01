package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ComboFoodConstituentDTO {
    private Long id;

    @NotNull
    private FoodDTO food;

    @DecimalMax("999.99")
    private BigDecimal amount;

    @Size(max=45)
    private String unit;
}
