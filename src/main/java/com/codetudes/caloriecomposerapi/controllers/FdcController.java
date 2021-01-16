package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.services.FdcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fdc")
public class FdcController {

    static final Logger LOG = LoggerFactory.getLogger(FdcController.class);

    @Autowired
    private FdcService fdcService;

    @GetMapping("/search")
    Page<FoodDTO> search(@RequestParam String query, @PageableDefault(value = 20, page = 0) Pageable pageable) {
        LOG.info("Received request to search fdc with query: {}, and page size: {}, and zero-indexed page number: {}", query, pageable.getPageSize(), pageable.getPageNumber());
        Page<FoodDTO> pagedResult = fdcService.search(query, pageable);
        LOG.info("Successfully searched fdc with query: {}", query);
        return pagedResult;
    }

    @GetMapping("/food/{fdcId}")
    FoodDTO read(@PathVariable String fdcId) {
        LOG.info("Received request to read fdc with fdcId {}", fdcId);
        FoodDTO foodDTO = fdcService.getFoodByFdcId(fdcId);
        LOG.info("Successfully read fdc food with fdcId {}", fdcId);
        return foodDTO;
    }


}
