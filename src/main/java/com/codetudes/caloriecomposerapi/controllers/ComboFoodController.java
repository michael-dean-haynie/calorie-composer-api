//package com.codetudes.caloriecomposerapi.controllers;
//
//import com.codetudes.caloriecomposerapi.contracts.ComboFoodDTO;
//import com.codetudes.caloriecomposerapi.services.ComboFoodService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/comboFood")
//public class ComboFoodController {
//    static final Logger LOG = LoggerFactory.getLogger(FoodController.class);
//
//    @Autowired
//    ComboFoodService comboFoodService;
//
//    @PostMapping
//    ResponseEntity<ComboFoodDTO> create(@Validated @RequestBody ComboFoodDTO comboFoodDTO) {
//        LOG.info("Received request to create comboFood");
//        ComboFoodDTO createdComboFood = comboFoodService.create(comboFoodDTO);
//        LOG.info("Successfully created comboFood with id {}", createdComboFood.getId());
//        return new ResponseEntity<>(createdComboFood, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    ResponseEntity read(@PathVariable Long id) {
//        LOG.info("Received request to read comboFood with id {}", id);
//        ComboFoodDTO comboFoodDTO = comboFoodService.read(id);
//        LOG.info("Successfully read comboFood with id {},", id);
//        return new ResponseEntity(comboFoodDTO, HttpStatus.OK);
//    }
//
//    @PutMapping()
//    ResponseEntity update(@Validated @RequestBody ComboFoodDTO comboFoodDTO) {
//        LOG.info("Received request to update comboFood with id {}", comboFoodDTO.getId());
//        ComboFoodDTO updatedComboFoodDTO = comboFoodService.update(comboFoodDTO);
//        LOG.info("Successfully updated comboFood with id {},", updatedComboFoodDTO.getId());
//        return new ResponseEntity(updatedComboFoodDTO, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    ResponseEntity delete(@PathVariable Long id) {
//        LOG.info("Received request to delete comboFood with id {}", id);
//        comboFoodService.delete(id);
//        LOG.info("Successfully deleted comboFood with id {},", id);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//}
