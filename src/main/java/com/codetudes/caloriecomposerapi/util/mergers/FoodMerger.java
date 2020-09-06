package com.codetudes.caloriecomposerapi.util.mergers;

import com.codetudes.caloriecomposerapi.config.MergeMapper;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.ConversionRatio;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.domain.Nutrient;
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

    public Food merge(FoodDTO foodDTO, Food food) {
        mergeMapper.map(foodDTO, food);

        // Clear and re-create nutrient entities
        food.getNutrients().clear();
        food.setNutrients(foodDTO.getNutrients().stream()
                .map(nutrientDTO -> modelMapper.map(nutrientDTO, Nutrient.class))
                .collect(Collectors.toList()));

        // Nutrients logically and physically own the relationship. Set it here.
        food.getNutrients().forEach(nutrient -> nutrient.setFood(food));

        // Clear and re-create conversion ratio entities
        food.getConversionRatios().clear();
        food.setConversionRatios(foodDTO.getConversionRatios().stream()
                .map(conversionRatioDTO -> modelMapper.map(conversionRatioDTO, ConversionRatio.class))
                .collect(Collectors.toList()));

        // Conversion ratios logically and physically own the relationship. Set it here.
        food.getConversionRatios().forEach(conversionRatio -> conversionRatio.setFood(food));

        return food;
    }
}
