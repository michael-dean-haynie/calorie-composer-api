package com.codetudes.caloriecomposerapi.controllers;

import com.codetudes.caloriecomposerapi.clients.FdcClient;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
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

    @Value("${foodDataCentral.apiEndpoint}")
    private String test;

    @GetMapping("/")
    Object helloWorld() {

//        Object response = fcdClient.getFoodItemByFdcId(167971L);
        Object response = userRepository.findAll();

        return response;
    }
}
