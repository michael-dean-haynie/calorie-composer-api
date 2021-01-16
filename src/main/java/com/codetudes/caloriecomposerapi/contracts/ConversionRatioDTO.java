package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import javax.validation.Valid;
import java.math.BigDecimal;

@Data
public class ConversionRatioDTO {
    private Long id;

    private BigDecimal amountA;

    @Valid
    private UnitDTO unitA;

    private String freeFormValueA;

    private BigDecimal amountB;

    @Valid
    private UnitDTO unitB;

    private String freeFormValueB;
}
