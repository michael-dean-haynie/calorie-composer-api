package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.clients.FdcClient;
import com.codetudes.caloriecomposerapi.contracts.fdc.FDCFood;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Value("${foodDataCentral.apiEndpoint}")
    private String test;

    @GetMapping("/")
    Object helloWorld() {

        FDCFood branded = fcdClient.getFoodItemByFdcId(414207L);
        FDCFood foundation = fcdClient.getFoodItemByFdcId(747444L);
        FDCFood srLegacy = fcdClient.getFoodItemByFdcId(169049L);
        FDCFood surveyFNDDS = fcdClient.getFoodItemByFdcId(784222L);


        return "all done";
    }
}
