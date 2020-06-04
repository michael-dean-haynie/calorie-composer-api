package com.codetudes.caloriecomposerapi.util.mappers;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.NutrientDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcBrandedFoodDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Component
public class BrandedFoodMapper {

    @Autowired
    ObjectMapper objectMapper;

    public FoodDTO map(LinkedHashMap<String, Object> fdcResponse) {

        // get mapping mostly done
        FoodDTO foodDTO = objectMapper.convertValue(fdcResponse, FoodDTO.class);

        // flatten and add nutrients
        FdcBrandedFoodDTO fdcBrandedFoodDTO = objectMapper.convertValue(fdcResponse, FdcBrandedFoodDTO.class);
        foodDTO.setNutrients(fdcBrandedFoodDTO.getFoodNutrients().stream().map(fdcFoodNutrientDTO -> {
            NutrientDTO nutrientDTO = new NutrientDTO();
            nutrientDTO.setName(fdcFoodNutrientDTO.getNutrient().getName());
            nutrientDTO.setUnitName(fdcFoodNutrientDTO.getNutrient().getUnitName());
            nutrientDTO.setAmount(fdcFoodNutrientDTO.getAmount());
            return nutrientDTO;
        }).collect(Collectors.toList()));

        return foodDTO;
    }
}
