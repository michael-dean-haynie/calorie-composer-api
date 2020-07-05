package com.codetudes.caloriecomposerapi.config;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import org.modelmapper.ModelMapper;

/**
 * Model mapper does not do a great job overwriting hibernate entity collections while merging DTO
 * updates onto managed entities. Skip those by using custom configured merge mapper and do manually.
 *
 * https://stackoverflow.com/questions/24665109/how-to-merge-input-from-a-web-service-to-a-jpa-entity
 * https://github.com/modelmapper/modelmapper/issues/477
 */
public class MergeMapper extends ModelMapper {
    MergeMapper() {
        super();
        // Food DTO -> Entity: skip collections
        this.typeMap(FoodDTO.class, Food.class)
                .addMappings(mpr -> mpr.skip(Food::setPortions))
                .addMappings(mpr -> mpr.skip(Food::setNutrients));

        // TODO: ComboFood

        // TODO: Plan
    }
}
