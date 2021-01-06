package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.contracts.UnitDTO;
import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.db.repositories.UnitRepository;
import com.codetudes.caloriecomposerapi.services.UnitService;
import com.codetudes.caloriecomposerapi.services.UserService;
import com.codetudes.caloriecomposerapi.util.enums.TokenUnit;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UserService userService;

    @Override
    public UnitDTO create(UnitDTO unitDTO) {
        Unit unit = modelMapper.map(unitDTO, Unit.class);
        if (!unitDTO.getIsDraft()){
            throw400IfMatchingUnitExists(unit);
        }
        return modelMapper.map(createEntity(unit), UnitDTO.class);
    }

    @Override
    public UnitDTO read(Long id) {
        Unit unit = unitRepository.findById(id).orElse(null);
        throw404IfNull(unit);

        return modelMapper.map(unit, UnitDTO.class);
    }

    @Override
    public List<UnitDTO> readAll() {
        return modelMapper.map(
                unitRepository.findByUserAndDraftOfIsNull(this.userService.getCurrentUser()),
                new TypeToken<List<UnitDTO>>() {}.getType()
        );
    }

    @Override
    public UnitDTO update(UnitDTO unitDTO) {
        Unit existingUnit = unitRepository.findById(unitDTO.getId()).orElse(null);
        throw404IfNull(existingUnit);

        modelMapper.map(unitDTO, existingUnit);

        // check if draft was removed
        if (unitDTO.getDraft() == null && existingUnit.getDraft() != null){
            existingUnit.getDraft().setDraftOf(null);
            existingUnit.setDraft(null);
        }

        if (existingUnit.getDraft() != null) {
            // Draft unit owns the relationship. Set it here.
            existingUnit.getDraft().setDraftOf(existingUnit);
            existingUnit.getDraft().setUser(existingUnit.getUser());
        }

        Unit updatedUnit = unitRepository.save(existingUnit);

        return modelMapper.map(updatedUnit, UnitDTO.class);
    }

    @Override
    public void delete(Long id) {
        Unit unit = unitRepository.findById(id).orElse(null);
        throw404IfNull(unit);

        unitRepository.deleteById(unit.getId());
    }

    @Override
    public Long getDraftCount() {
        return this.unitRepository.countByIsDraftTrue();
    }

    @Override
    public Unit createEntity(Unit unit) {
        unit.setUser(userService.getCurrentUser());
        if (unit.getDraft() != null) {
            // Draft unit owns the relationship. Set it here.
            unit.getDraft().setDraftOf(unit);
            unit.getDraft().setUser(unit.getUser());
        }
        return unitRepository.save(unit);
    }

    @Override
    public Unit resolveUnit(Unit unit) {
        if (unit == null) {
            return null;
        }

        // default isDraft to false
        if (unit.getIsDraft() == null)  {
            unit.setIsDraft(false);
        }

        if (unit.getId() == null) {
            // are there any existing units that are the "same"?
            Unit matchingUnit = this.findMatchingUnit(unit);
            if (matchingUnit != null){
                return matchingUnit;
            } else {
                return createEntity(unit);
            }
        } else {
            Unit existingUnit = unitRepository.findById(unit.getId()).orElse(null);
            if (existingUnit == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unit '" + unit.getAbbreviation() + "' not found.");
            }
            return existingUnit;
        }
    }

    @Override
    public UnitDTO createTokenUnitDTO(TokenUnit tokenUnit) {
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setIsDraft(false);
        unitDTO.setSingular(tokenUnit.name());
        unitDTO.setPlural(tokenUnit.name());
        unitDTO.setAbbreviation(tokenUnit.name());
        return unitDTO;
    }

    private void throw404IfNull(Unit existingUnit) {
        if (existingUnit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Unit not found.");
        }
    }

    private Unit findMatchingUnit(Unit unit) {
        return unitRepository.findFirstByUserAndAbbreviationAndDraftOfIsNull(
                userService.getCurrentUser(), unit.getAbbreviation());
    }

    private void throw400IfMatchingUnitExists(Unit unit) {
        boolean matchingUnitExists = findMatchingUnit(unit) != null;
        if (matchingUnitExists){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A matching unit already exists.");
        }
    }
}
