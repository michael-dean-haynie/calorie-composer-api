package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.clients.FdcClient;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcFoodDTO;
import com.codetudes.caloriecomposerapi.services.FdcService;
import com.codetudes.caloriecomposerapi.util.factories.FoodDTOFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class FdcServiceImpl implements FdcService {

    @Autowired
    FdcClient fdcClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FoodDTOFactory foodDTOFactory;

    public FoodDTO getFoodByFdcId(Long fdcId) {
        LinkedHashMap<String, Object> fdcResponse = fdcClient.getFoodByFdcId(fdcId);

        // convert to FdcFoodDTO to determine food data type
        FdcFoodDTO fdcFoodDTO = objectMapper.convertValue(fdcResponse, FdcFoodDTO.class);


        return foodDTOFactory.createFoodDTO(fdcResponse, fdcFoodDTO.getDataType());
    }

}
