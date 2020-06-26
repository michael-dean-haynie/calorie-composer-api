package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.SearchResultDTO;
import com.codetudes.caloriecomposerapi.services.FdcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fdc")
public class FdcController {

    static final Logger LOG = LoggerFactory.getLogger(FdcController.class);

    @Autowired
    private FdcService fdcService;

    @GetMapping("/search")
    SearchResultDTO search(@RequestParam String query) {
        LOG.info("Received request to search fdc with query: {}", query);
        SearchResultDTO searchResultDTO = fdcService.search(query);
        LOG.info("Successfully searched fdc with query: {}", query);
        return fdcService.search(query);
    }

    @GetMapping("/food/{fdcId}")
    FoodDTO read(@PathVariable String fdcId) {
        LOG.info("Received request to read fdc with fdcId {}", fdcId);
        FoodDTO foodDTO = fdcService.getFoodByFdcId(fdcId);
        LOG.info("Successfully read fdc food with fdcId {}", fdcId);
        return foodDTO;
    }


}
