package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.contracts.UnitDTO;
import com.codetudes.caloriecomposerapi.services.UnitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unit")
@Log4j2
public class UnitController {

    @Autowired
    UnitService unitService;

    @PostMapping
    ResponseEntity<UnitDTO> create(@Validated @RequestBody UnitDTO unitDTO) {
        log.info("Received request to create unit");
        UnitDTO createdUnit = unitService.create(unitDTO);
        log.info("Successfully created unit with id {}", createdUnit.getId());
        return new ResponseEntity<>(createdUnit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<UnitDTO> read(@PathVariable Long id) {
        log.info("Received request to read unit with id {}.", id);
        UnitDTO unitDTO = unitService.read(id);
        log.info("Successfully read unit with id {}.", id);
        return new ResponseEntity(unitDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<UnitDTO>> readAll() {
        log.info("Received request to read all units for current user");
        List<UnitDTO> unitDTOs = unitService.readAll();
        log.info("Successfully read all units for current user.");
        return new ResponseEntity(unitDTOs, HttpStatus.OK);
    }

    @PutMapping()
    ResponseEntity<UnitDTO> update(@Validated @RequestBody UnitDTO unitDTO) {
        log.info("Received request to update unit with id {}.", unitDTO.getId());
        UnitDTO updatedUnitDTO = unitService.update(unitDTO);
        log.info("Successfully updated unit with id {}.", updatedUnitDTO.getId());
        return new ResponseEntity(updatedUnitDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable Long id) {
        log.info("Received request to delete unit with id {}.", id);
        unitService.delete(id);
        log.info("Successfully deleted unit with id {}.", id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/draft/count")
    ResponseEntity<Long> getDraftCount() {
        log.info("Received request to get unit draft count");
        Long count = this.unitService.getDraftCount();
        log.info("Successfully returned unit draft count of {}", count);
        return new ResponseEntity(count, HttpStatus.OK);
    }
}