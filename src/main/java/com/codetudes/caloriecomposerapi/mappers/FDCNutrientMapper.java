package com.codetudes.caloriecomposerapi.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FDCNutrientMapper {

    @Autowired
    private ObjectMapper objectMapper;

//    public FDCNutrient map(Object source) {
//        if (!(source instanceof HashMap)){
//            throw new IllegalArgumentException("Source object must be instance of HashMap");
//        }
//
//        HashMap<String, Object> sourceAsMap = (HashMap<String, Object>) source;
//
//        FDCNutrient fdcNutrient = new FDCNutrient();
////        fdcNutrient.setName(sourceAsMap.get("name"));
//
//
//
//    }
}
