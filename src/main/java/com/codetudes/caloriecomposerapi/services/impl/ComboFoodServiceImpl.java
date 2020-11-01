package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.contracts.ComboFoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.ComboFood;
import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.ComboFoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.ComboFoodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ComboFoodServiceImpl implements ComboFoodService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ComboFoodRepository comboFoodRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ComboFoodDTO create(ComboFoodDTO comboFoodDTO) {
        ComboFood comboFood = modelMapper.map(comboFoodDTO, ComboFood.class);

        // ComboFoodConstituents and ComboFoodConversionRatios own the relationship
        comboFood.getConstituents().forEach(constituent -> constituent.setComboFood(comboFood));
        comboFood.getConversionRatios().forEach(cvRat -> cvRat.setComboFood(comboFood));

        // TODO: Don't hard code this
        List<User> users = userRepository.findAll();
        if (users.size() > 0){
            comboFood.setUser(users.get(0));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Hard-coded user unable to be found while creating food.");
        }

        ComboFood savedComboFood = comboFoodRepository.save(comboFood);
        return modelMapper.map(savedComboFood, ComboFoodDTO.class);
    }

    @Override
    public ComboFoodDTO read(Long id) {
        ComboFood comboFood = comboFoodRepository.findById(id).orElse(null);
        throw404IfNull(comboFood);

        return modelMapper.map(comboFood, ComboFoodDTO.class);
    }

    @Override
    public ComboFoodDTO update(ComboFoodDTO comboFoodDTO) {
//        ComboFood existingComboFood = comboFoodRepository.findById(comboFoodDTO.getId()).orElse(null);
//        throw404IfNull(existingComboFood);
//
//        // model mapper freaking out - not type mapping as expected
//        existingComboFood.setDescription(comboFoodDTO.getDescription());
//
//        // food amounts
//        existingComboFood.getFoodAmounts().clear();
//        existingComboFood.setFoodAmounts(comboFoodDTO.getFoodAmounts().stream()
//                .map(foodAmountDTO -> {
//                    ComboFoodFoodAmount foodAmount = modelMapper.map(foodAmountDTO, ComboFoodFoodAmount.class);
//                    // assign relationship for comboFood
//                    foodAmount.setComboFood(existingComboFood);
//                    // assign relationship for food
//                    foodAmount.setFood(foodRepository.findById(foodAmountDTO.getFood().getId()).orElse(null));
//                    return foodAmount;
//                })
//                .collect(Collectors.toList())
//        );
//
//        // portions
//        existingComboFood.getPortions().clear();
//        existingComboFood.setPortions(comboFoodDTO.getPortions().stream()
//                .map(portionDTO -> {
//                    ComboFoodPortion foodPortion = modelMapper.map(portionDTO, ComboFoodPortion.class);
//                    // assign relationship for comboFood
//                    foodPortion.setComboFood(existingComboFood);
//                    return foodPortion;
//                })
//                .collect(Collectors.toList()));
//
//        ComboFood updatedComboFood = comboFoodRepository.save(existingComboFood);
//        return modelMapper.map(updatedComboFood, ComboFoodDTO.class);
        return null;
    }

    @Override
    public void delete(Long id) {
        ComboFood comboFood = comboFoodRepository.findById(id).orElse(null);
        throw404IfNull(comboFood);

        comboFoodRepository.deleteById(comboFood.getId());
    }

    private void throw404IfNull(ComboFood existingComboFood) {
        if (existingComboFood == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ComboFood not found.");
        }
    }
}
