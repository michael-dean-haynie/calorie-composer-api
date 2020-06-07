package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.contracts.SearchResultDTO;
import com.codetudes.caloriecomposerapi.services.FdcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fdc")
public class FdcController {

    @Autowired
    private FdcService fdcService;

    @GetMapping("/search")
    SearchResultDTO search(@RequestParam String query) {
        return fdcService.search(query);
    }
}
