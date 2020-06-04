package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NutrientDTO {
    private String name;

    private String unitName;

    private BigDecimal amount;
}
