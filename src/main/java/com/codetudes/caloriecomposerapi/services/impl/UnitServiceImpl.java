package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.contracts.UnitDTO;
import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.UnitRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.UnitService;
import com.codetudes.caloriecomposerapi.util.enums.TokenUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Unit create(Unit unit) {
        unit.setUser(getUser());
        return unitRepository.save(unit);
    }

    @Override
    public Unit resolveUnit(Unit unit) {
        if (unit == null) {
            return null;
        }

        if (unit.getId() == null) {
            // are there any existing units that are the "same"?
            Unit matchingUnit = this.findMatchingUnit(unit);
            if (matchingUnit != null){
                return matchingUnit;
            } else {
                return create(unit);
            }
        } else {
            Unit existingUnit = unitRepository.findById(unit.getId()).orElse(null);
            if (existingUnit == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unit not found.");
            }
            return existingUnit;
        }
    }

    @Override
    public UnitDTO createTokenUnitDTO(TokenUnit tokenUnit) {
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setSingular(tokenUnit.name());
        unitDTO.setPlural(tokenUnit.name());
        unitDTO.setAbbreviation(tokenUnit.name());
        return unitDTO;
    }

    private User getUser() {
        // TODO: don't hard code this
        List<User> users = userRepository.findAll();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Hard-coded user unable to be found while creating unit.");
        }
    }

    private Unit findMatchingUnit(Unit unit) {
        return unitRepository.findFirstByUserAndSingularAndPluralAndAbbreviation(
                getUser(), unit.getSingular(), unit.getPlural(), unit.getAbbreviation());
    }
}
