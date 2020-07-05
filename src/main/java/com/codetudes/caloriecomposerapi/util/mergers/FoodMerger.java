package com.codetudes.caloriecomposerapi.util.mergers;

import com.codetudes.caloriecomposerapi.config.MergeMapper;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.domain.Nutrient;
import com.codetudes.caloriecomposerapi.db.domain.Portion;
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

        // Clear and re-create portion entities
        food.getPortions().clear();
        food.setPortions(foodDTO.getPortions().stream()
                .map(portionDTO -> modelMapper.map(portionDTO, Portion.class))
                .collect(Collectors.toList()));

        // Portions logically and physically own the relationship. Set it here.
        food.getPortions().forEach(portion -> portion.setFood(food));

        return food;
    }
}
