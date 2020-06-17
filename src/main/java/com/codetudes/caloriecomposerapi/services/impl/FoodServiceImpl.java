package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.config.ApplicationConfig;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.NutrientDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.domain.Nutrient;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.FoodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ApplicationConfig.FoodPatchMapper foodPatchMapper;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public FoodDTO create(FoodDTO foodDTO) {
        final Food food = modelMapper.map(foodDTO, Food.class);

        // TODO: Don't hard code this
        userRepository.findById(1L).ifPresentOrElse(
                user -> food.setUser(user),
                () -> {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Hard-coded user unable to be found while creating food.");
                }
        );

        // save and flush to get db generated id in response
        Food savedFood = foodRepository.save(food);
        return modelMapper.map(savedFood, FoodDTO.class);
    }

    @Override
    public FoodDTO read(Long id) {
        Food food = foodRepository.findById(id).orElse(null);
        throw404IfNull(food);

        return modelMapper.map(food, FoodDTO.class);
    }

    @Override
    public FoodDTO update(FoodDTO foodDTO) {
        Food existingFood = foodRepository.findById(foodDTO.getId()).orElse(null);
        throw404IfNull(existingFood);

        // map most fields from DTO onto entity
        foodPatchMapper.map(foodDTO, existingFood);

        // patch map nested nutrient entities
        updateNestedNutrients(existingFood.getNutrients(), foodDTO.getNutrients());

        Food updatedFood = foodRepository.save(existingFood);

        return modelMapper.map(updatedFood, FoodDTO.class);
    }

    @Override
    public void delete(Long id) {
        Food food = foodRepository.findById(id).orElse(null);
        throw404IfNull(food);

        foodRepository.deleteById(food.getId());
    }

    private void throw404IfNull(Food existingFood){
        if (existingFood == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Food not found.");
        }
    }

    // TODO: hide in shame
    private List<Nutrient> updateNestedNutrients(List<Nutrient> existing, List<NutrientDTO> patch) {
        // update and add
        patch.forEach(pN -> {
            boolean isExisting = existing.stream().anyMatch(eN -> eN.getId().equals(pN.getId()));
            if (isExisting) {
                existing.stream().filter(eN -> eN.getId().equals(pN.getId()))
                        .forEach(eN -> modelMapper.map(pN, eN));
            }

            boolean isNew = !isExisting;
            if (isNew) {
                existing.add(modelMapper.map(pN, Nutrient.class));
            }
        });

        // remove
        List<Nutrient> nutrientsToRemove = new ArrayList();
        existing.forEach(eN -> {
            boolean isRemoved = !patch.stream().anyMatch(pN -> pN.getId().equals(eN.getId()));
            if (isRemoved) {
                nutrientsToRemove.add(eN);
            }
        });
        nutrientsToRemove.forEach(nutrient -> {
            existing.remove(nutrient);
        });

        return existing;
    }
}
