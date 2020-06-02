package com.codetudes.caloriecomposerapi.contracts.fdc;

import com.codetudes.caloriecomposerapi.contracts.fdc.enums.FDCDataType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FDCFood {
    private FDCDataType dataType;

    private String description;
}
