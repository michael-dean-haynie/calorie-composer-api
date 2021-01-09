package com.codetudes.caloriecomposerapi.util.mergers;

import com.codetudes.caloriecomposerapi.config.MergeMapper;
import com.codetudes.caloriecomposerapi.contracts.UnitDTO;
import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitMerger {
    @Autowired
    UnitService unitService;
    @Autowired
    MergeMapper mergeMapper;

    Unit merge(UnitDTO unitDTO, Unit unit){
        if (unitShouldBeNull(unitDTO)) {
            return null;
        }
        // only really need to update the abbreviation
        // TODO: could probably make better with method that only requires abbreviation
        Unit unitToResolve = new Unit();
        unitToResolve.setAbbreviation(unitDTO.getAbbreviation());
        return unitService.resolveUnit(unitToResolve);
    }

    private boolean unitShouldBeNull(UnitDTO unitDTO) {
        return unitDTO == null || unitDTO.getAbbreviation() == null;
    }
}
