package com.codetudes.caloriecomposerapi.util.mergers;

import com.codetudes.caloriecomposerapi.config.MergeMapper;
import com.codetudes.caloriecomposerapi.contracts.NutrientDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.domain.Nutrient;
import com.codetudes.caloriecomposerapi.db.repositories.NutrientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NutrientMerger {
    @Autowired
    private MergeMapper mergeMapper;
    @Autowired
    private UnitMerger unitMerger;
    @Autowired
    private NutrientRepository nutrientRepository;

    Nutrient merge(NutrientDTO nutrientDTO, Nutrient nutrient, Food food) {
        boolean needsPersisted = false;
        if (nutrient == null) {
            if (food.getId() != null) {
                needsPersisted = true;
            }
            nutrient = new Nutrient();
        }

        nutrient.setFood(food);
        nutrient.setName(nutrientDTO.getName());
        nutrient.setAmount(nutrientDTO.getAmount());
        nutrient.setUnit(unitMerger.merge(nutrientDTO.getUnit(), nutrient.getUnit()));

        if (needsPersisted) {
            nutrientRepository.save(nutrient);
        }
        return nutrient;
    }
}
