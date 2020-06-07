package com.codetudes.caloriecomposerapi.contracts.fdc;

import com.codetudes.caloriecomposerapi.contracts.fdc.enums.FdcDataType;
import lombok.Data;

@Data
public class FdcFoodDTO {
    private String fdcId;

    private FdcDataType dataType;

    private String description;
}
