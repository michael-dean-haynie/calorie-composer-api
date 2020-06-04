package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;

public interface FdcService {

    FoodDTO getFoodByFdcId(Long fdcId);
}
