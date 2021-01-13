package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;

import java.util.List;

public interface FoodService {
    FoodDTO create(FoodDTO foodDTO);

    FoodDTO read(Long id);

    FoodDTO update(FoodDTO foodDTO);

    void delete(Long id);

    List<FoodDTO> readAllFoods();

    /**
     * Returns a list of foods that have a draft or are a draft but not of an existing food
     * @return
     */
    List<FoodDTO> readDrafts();

    List<FoodDTO> search(String query);
}
