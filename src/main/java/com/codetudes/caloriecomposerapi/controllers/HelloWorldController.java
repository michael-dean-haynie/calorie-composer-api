package com.codetudes.caloriecomposerapi.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

//    @Autowired
//    private FdcClient fcdClient;

    @Value("${foodDataCentral.apiEndpoint}")
    private String test;

    @GetMapping("/")
    String helloWorld() {

//        Object response = fcdClient.getFoodItemByFdcId(167971L);

        return test;
    }
}
