package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.services.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
public class FoodController {

    static final Logger LOG = LoggerFactory.getLogger(FoodController.class);

    @Autowired
    FoodService foodService;

    @PostMapping
    ResponseEntity<FoodDTO> create(@Validated @RequestBody FoodDTO foodDTO) {
        LOG.info("Received request to create food");
        FoodDTO createdFood = foodService.create(foodDTO);
        LOG.info("Successfully created food with id {}", foodDTO.getId());
        return new ResponseEntity<>(createdFood, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity read(@PathVariable Long id) {
        LOG.info("Received request to read food with id {}", id);
        FoodDTO foodDTO = foodService.read(id);
        LOG.info("Successfully read food with id {},", id);
        return new ResponseEntity(foodDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable Long id) {
        LOG.info("Received request to delete food with id {}", id);
        foodService.delete(id);
        LOG.info("Successfully deleted food with id {},", id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
