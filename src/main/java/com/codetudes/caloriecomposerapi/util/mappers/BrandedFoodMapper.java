package com.codetudes.caloriecomposerapi.util.mappers;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.NutrientDTO;
import com.codetudes.caloriecomposerapi.contracts.PortionDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcBrandedFoodDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Component
public class BrandedFoodMapper {

    @Autowired
    ObjectMapper objectMapper;

    public FoodDTO map(LinkedHashMap<String, Object> fdcResponse) {

        // start by getting mapping mostly done
        FoodDTO foodDTO = objectMapper.convertValue(fdcResponse, FoodDTO.class);

        FdcBrandedFoodDTO fdcBrandedFoodDTO = objectMapper.convertValue(fdcResponse, FdcBrandedFoodDTO.class);

        // add portions
        foodDTO.setPortions(new ArrayList<>());

        PortionDTO refPortion = new PortionDTO();
        refPortion.setIsNutrientRefPortion(true);
        refPortion.setBaseUnitName(fdcBrandedFoodDTO.getServingSizeUnit());
        refPortion.setBaseUnitAmount(new BigDecimal(100));
        foodDTO.getPortions().add(refPortion);

        PortionDTO ssPortion = new PortionDTO();
        ssPortion.setIsServingSizePortion(true);
        ssPortion.setBaseUnitName(fdcBrandedFoodDTO.getServingSizeUnit());
        ssPortion.setBaseUnitAmount(fdcBrandedFoodDTO.getServingSize());
        ssPortion.setDescription(fdcBrandedFoodDTO.getHouseholdServingFullText());
        foodDTO.getPortions().add(ssPortion);

        // flatten and add nutrients
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
