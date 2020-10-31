package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class NutrientDTO {
    private Long id;

    @NotBlank
    @Size(max=45)
    private String name;

    @NotBlank
    @Size(max=45)
    private String unit;

    @NotNull
    @DecimalMax("999.99")
    private BigDecimal amount;
}
