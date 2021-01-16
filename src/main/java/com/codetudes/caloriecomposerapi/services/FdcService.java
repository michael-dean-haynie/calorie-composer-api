package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FdcService {

    FoodDTO getFoodByFdcId(String fdcId);

    Page<FoodDTO> search(String query, Pageable pageable);
}
