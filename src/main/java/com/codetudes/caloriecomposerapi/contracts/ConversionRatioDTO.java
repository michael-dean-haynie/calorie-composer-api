package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ConversionRatioDTO {
    private Long id;

    @DecimalMax("999.99")
    private BigDecimal amountA;

    @Valid
    private UnitDTO unitA;

    @Size(max=50)
    private String freeFormValueA;

    @DecimalMax("999.99")
    private BigDecimal amountB;

    @Valid
    private UnitDTO unitB;

    @Size(max=50)
    private String freeFormValueB;
}
