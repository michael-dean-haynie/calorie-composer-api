package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;

import java.util.List;

public interface FoodService {
    FoodDTO create(FoodDTO foodDTO);

    FoodDTO read(Long id);

    FoodDTO update(FoodDTO foodDTO);

    void delete(Long id);

    List<FoodDTO> search(String query);
}
