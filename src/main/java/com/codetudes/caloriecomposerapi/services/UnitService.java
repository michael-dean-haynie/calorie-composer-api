package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.contracts.UnitDTO;
import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.util.enums.TokenUnit;

public interface UnitService {
    /**
     * Accepts a Unit entity, persists it to the database and returns the result
     * @param unit the unit entity
     * @return the persisted result
     */
    Unit create(Unit unit);

    /**
     * Checks if same unit already exists in db. If it does, returns that.
     * If it doesn't exist, it creates it and persists it so it can be used to associate other entities
     * @param unit the unit to resolve
     * @return the resolved unit
     */
    Unit resolveUnit(Unit unit);

    /**
     * Helper that creates and populates a unit DTO for a TokenUnit
     * @param tokenUnit The token unit
     * @return A UnitDTO for the token unit
     */
    UnitDTO createTokenUnitDTO(TokenUnit tokenUnit);
}
