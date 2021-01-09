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

import java.util.ArrayList;
import java.util.List;

@Component
public class FoodMerger {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MergeMapper mergeMapper;

    @Autowired
    private UnitService unitService;
    @Autowired
    private UnitMerger unitMerger;
    @Autowired
    private NutrientMerger nutrientMerger;
    @Autowired
    private ConversionRatioMerger conversionRatioMerger;

    public Food merge(FoodDTO foodDTO, Food food) {

        // simple top level properties
        food.setIsDraft(foodDTO.getIsDraft());
        food.setFdcId(foodDTO.getFdcId());
        food.setDescription(foodDTO.getDescription());
        food.setBrandOwner(foodDTO.getBrandOwner());
        food.setIngredients(foodDTO.getIngredients());

        // top level units
        food.setSsrDisplayUnit(unitMerger.merge(foodDTO.getSsrDisplayUnit(), food.getSsrDisplayUnit()));
        food.setCsrDisplayUnit(unitMerger.merge(foodDTO.getCsrDisplayUnit(), food.getCsrDisplayUnit()));

        // Nutrients
        List<Nutrient> oldNutrients = new ArrayList<>(food.getNutrients());
        food.getNutrients().clear();
        foodDTO.getNutrients().forEach(nutrientDTO -> {
            Nutrient oldEntity = oldNutrients.stream()
                    .filter(nutrient -> {
                        return nutrientDTO.getId() != null && nutrientDTO.getId().equals(nutrient.getId());
                    }).findFirst().orElse(null);
            Nutrient newEntity = nutrientMerger.merge(nutrientDTO, oldEntity, food);
            food.getNutrients().add(newEntity);
        });

        // Conversion Ratios
        List<ConversionRatio> oldCvRats = new ArrayList<>(food.getConversionRatios());
        food.getConversionRatios().clear();
        foodDTO.getConversionRatios().forEach(cvRatDTO -> {
            ConversionRatio oldEntity = oldCvRats.stream()
                    .filter(cvRat -> {
                        return cvRatDTO.getId() != null && cvRatDTO.getId().equals(cvRat.getId());
                    }).findFirst().orElse(null);
            ConversionRatio newEntity = conversionRatioMerger.merge(cvRatDTO, oldEntity, food);
            food.getConversionRatios().add(newEntity);
        });

        // Merge nested food draft if exists
        if (foodDTO.getDraft() != null) {
            if (food.getDraft() == null){
                food.setDraft(new Food());
            }
            food.setDraft(merge(foodDTO.getDraft(), food.getDraft()));
            food.getDraft().setIsDraft(true);
            // draft owns the relationship. set it here
            food.getDraft().setDraftOf(food);
        }

        // detach existing draft if dto specifies it as null (should be cleaned up as orphan?)
        else {
            if (food.getDraft() != null) {
                food.getDraft().setDraftOf(null);
                food.setDraft(null);
            }
        }

        return food;
    }
}
