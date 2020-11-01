package com.codetudes.caloriecomposerapi.util.mergers;

import com.codetudes.caloriecomposerapi.config.MergeMapper;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.ConversionRatio;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.domain.Nutrient;
import com.codetudes.caloriecomposerapi.services.UnitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FoodMerger {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    MergeMapper mergeMapper;

    @Autowired
    UnitService unitService;

    public Food  merge(FoodDTO foodDTO, Food food) {
        mergeMapper.map(foodDTO, food);

        food.setSsrDisplayUnit(unitService.resolveUnit(food.getSsrDisplayUnit()));
        food.setCsrDisplayUnit(unitService.resolveUnit(food.getCsrDisplayUnit()));

        // Clear and re-create nutrient entities
        food.getNutrients().clear();
        food.setNutrients(foodDTO.getNutrients().stream()
                .map(nutrientDTO -> modelMapper.map(nutrientDTO, Nutrient.class))
                .collect(Collectors.toList()));

        food.getNutrients().forEach(nutrient -> {
            // Nutrients logically and physically own the relationship. Set it here.
            nutrient.setFood(food);

            // Resolve units for nutrients
            nutrient.setUnit(unitService.resolveUnit(nutrient.getUnit()));
        });

        // Clear and re-create conversion ratio entities
        food.getConversionRatios().clear();
        food.setConversionRatios(foodDTO.getConversionRatios().stream()
                .map(conversionRatioDTO -> modelMapper.map(conversionRatioDTO, ConversionRatio.class))
                .collect(Collectors.toList()));

        food.getConversionRatios().forEach(cvRat -> {
            // Conversion ratios logically and physically own the relationship. Set it here.
            cvRat.setFood(food);

            // Resolve units for conversion ratios
            cvRat.setUnitA(unitService.resolveUnit(cvRat.getUnitA()));
            cvRat.setUnitB(unitService.resolveUnit(cvRat.getUnitB()));
        });

        return food;
    }
}
