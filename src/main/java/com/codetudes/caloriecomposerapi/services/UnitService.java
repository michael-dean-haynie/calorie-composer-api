package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.db.domain.Unit;

public interface UnitService {
    /**
     * Accepts a Unit entity, persists it to the database and returns the result
     * @param unit the unit entity
     * @return the persisted result
     */
    Unit create(Unit unit);
}
