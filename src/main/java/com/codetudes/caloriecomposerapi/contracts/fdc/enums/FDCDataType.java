package com.codetudes.caloriecomposerapi.contracts.fdc.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FDCDataType {
    @JsonProperty("Branded")
    BRANDED,
    @JsonProperty("Foundation")
    FOUNDATION,
    @JsonProperty("SR Legacy")
    SR_LEGACY,
    @JsonProperty("Survey (FNDDS)")
    SURVEY_FNDDS;
}
