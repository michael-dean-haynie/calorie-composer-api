package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;

public interface FoodService {
    FoodDTO create(FoodDTO foodDTO);
}
