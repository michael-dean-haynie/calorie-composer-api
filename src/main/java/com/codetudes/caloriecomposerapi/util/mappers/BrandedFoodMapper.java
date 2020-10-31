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

        // Add conversion ratio for constituents reference size
        ConversionRatioDTO constituentsRefCR = new ConversionRatioDTO();
        constituentsRefCR.setAmountA(new BigDecimal(1));
        constituentsRefCR.setUnitA("CONSTITUENTS_SIZE_REF");
        constituentsRefCR.setAmountB(new BigDecimal(100));
        constituentsRefCR.setUnitB(fdcBrandedFoodDTO.getServingSizeUnit());
        foodDTO.getConversionRatios().add(constituentsRefCR);

        // Add conversion ratio for serving size reference
        ConversionRatioDTO servingSizeRefCR = new ConversionRatioDTO();
        servingSizeRefCR.setAmountA(new BigDecimal(1));
        servingSizeRefCR.setUnitA("SERVING_SIZE_REF");
        servingSizeRefCR.setAmountB(fdcBrandedFoodDTO.getServingSize());
        servingSizeRefCR.setUnitB(fdcBrandedFoodDTO.getServingSizeUnit());
        foodDTO.getConversionRatios().add(servingSizeRefCR);

        // Add conversion ratio for free-form household serving
        if (null != fdcBrandedFoodDTO.getHouseholdServingFullText()) {
            ConversionRatioDTO householdRefCR = new ConversionRatioDTO();
            householdRefCR.setAmountA(new BigDecimal(1));
            householdRefCR.setUnitA("SERVING_SIZE_REF");
            householdRefCR.setFreeFormValueB(fdcBrandedFoodDTO.getHouseholdServingFullText());
            foodDTO.getConversionRatios().add(householdRefCR);
        }

        // flatten and add nutrients
        foodDTO.setNutrients(fdcBrandedFoodDTO.getFoodNutrients().stream().map(fdcFoodNutrientDTO -> {
            NutrientDTO nutrientDTO = new NutrientDTO();
            nutrientDTO.setName(fdcFoodNutrientDTO.getNutrient().getName());
            nutrientDTO.setUnit(fdcFoodNutrientDTO.getNutrient().getNutrientUnit().getName());
            nutrientDTO.setAmount(fdcFoodNutrientDTO.getAmount());
            return nutrientDTO;
        }).collect(Collectors.toList()));

        return foodDTO;
    }
}
