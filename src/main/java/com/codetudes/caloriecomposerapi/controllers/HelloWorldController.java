package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.clients.FdcClient;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.FdcService;
import com.codetudes.caloriecomposerapi.services.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private FdcClient fcdClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FdcService fdcService;

    @Autowired
    FoodService foodService;

    @Value("${foodDataCentral.apiEndpoint}")
    private String test;

    @GetMapping("/{fdcId}")
    FoodDTO helloWorld(@PathVariable String fdcId) {

//        Object branded = fcdClient.getFoodByFdcId(414207L);
//        Object foundation = fcdClient.getFoodByFdcId(746782L);
//        Object srLegacy = fcdClient.getFoodByFdcId(169049L);
//        Object surveyFNDDS = fcdClient.getFoodByFdcId(784222L);

//        FoodDTO foodDTO = fdcService.getFoodByFdcId("769876");
//        FoodDTO savedFoodDTO = foodService.create(foodDTO);
//        return savedFoodDTO;

        return fdcService.getFoodByFdcId(fdcId);
    }
}
