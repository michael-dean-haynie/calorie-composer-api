package com.codetudes.caloriecomposerapi.util.mappers;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcSearchResultFoodDTO;
import org.springframework.stereotype.Component;

@Component
public class SearchResultFoodMapper {

    public FoodDTO map(FdcSearchResultFoodDTO searchResultFood) {

        FoodDTO result = new FoodDTO();
        result.setFdcId(searchResultFood.getFdcId());
        result.setDescription(searchResultFood.getDescription());
        result.setBrandOwner(searchResultFood.getBrandOwner());
        result.setIngredients(searchResultFood.getIngredients());

        return result;
    }
}
