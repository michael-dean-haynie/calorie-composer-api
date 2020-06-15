package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    FoodService foodService;

    @PostMapping
    FoodDTO create(@Validated @RequestBody FoodDTO foodDTO) {
        return foodService.create(foodDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    ResponseEntity<String> delete(@PathVariable Long id) {
        foodService.delete(id);
        return new ResponseEntity<String>("Food deleted", HttpStatus.OK);
    }
}
