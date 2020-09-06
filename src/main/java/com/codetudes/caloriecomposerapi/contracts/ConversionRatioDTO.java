package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ConversionRatioDTO {
    private Long id;

    @DecimalMax("999.99")
    private BigDecimal amountA;

    @Size(max=20)
    private String unitA;

    @DecimalMax("999.99")
    private BigDecimal amountB;

    @Size(max=20)
    private String unitB;
}