package com.codetudes.caloriecomposerapi.util.mappers;

import com.codetudes.caloriecomposerapi.contracts.ConversionRatioDTO;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.NutrientDTO;
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

        // Add conversion ratios
        foodDTO.setConversionRatios(new ArrayList<>());

        ConversionRatioDTO constituentsRefCR = new ConversionRatioDTO();
        constituentsRefCR.setAmountA(new BigDecimal(1));
        constituentsRefCR.setUnitA("CONSTITUENTS_REF");
        constituentsRefCR.setAmountB(new BigDecimal(100));
        constituentsRefCR.setUnitB(fdcBrandedFoodDTO.getServingSizeUnit());
        foodDTO.getConversionRatios().add(constituentsRefCR);

        ConversionRatioDTO servingSizeRefCR = new ConversionRatioDTO();
        servingSizeRefCR.setAmountA(new BigDecimal(1));
        servingSizeRefCR.setUnitA("SERVING_SIZE_REF");
        servingSizeRefCR.setAmountB(fdcBrandedFoodDTO.getServingSize());
        servingSizeRefCR.setUnitB(fdcBrandedFoodDTO.getServingSizeUnit());
        // TODO: Add household measure back in somehow like maybe parsing fdcBrandedFoodDTO.getHouseholdServingFullText()
        foodDTO.getConversionRatios().add(servingSizeRefCR);

        // flatten and add nutrients
        foodDTO.setNutrients(fdcBrandedFoodDTO.getFoodNutrients().stream().map(fdcFoodNutrientDTO -> {
            NutrientDTO nutrientDTO = new NutrientDTO();
            nutrientDTO.setName(fdcFoodNutrientDTO.getNutrient().getName());
            nutrientDTO.setUnit(fdcFoodNutrientDTO.getNutrient().getUnitName());
            nutrientDTO.setScalar(fdcFoodNutrientDTO.getAmount());
            return nutrientDTO;
        }).collect(Collectors.toList()));

        return foodDTO;
    }
}
