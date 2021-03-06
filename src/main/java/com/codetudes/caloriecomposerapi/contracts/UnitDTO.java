package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

@Data
public class UnitDTO {
    private Long id;

    private Boolean isDraft;

    private String singular;

    private String plural;

    private String abbreviation;

    private UnitDTO draft;
}
