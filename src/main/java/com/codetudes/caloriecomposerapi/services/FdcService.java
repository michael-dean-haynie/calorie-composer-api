package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.SearchResultDTO;

public interface FdcService {

    FoodDTO getFoodByFdcId(String fdcId);

    SearchResultDTO search(String query);
}
