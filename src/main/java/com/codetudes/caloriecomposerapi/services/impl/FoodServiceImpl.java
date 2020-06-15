package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.FoodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    ModelMapper modelMapper;

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

        Food savedFood = foodRepository.save(food);
        return modelMapper.map(savedFood, FoodDTO.class);
    }

    @Override
    public FoodDTO read(Long id) {
        Food food = foodRepository.findById(id).orElse(null);

        if (food == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Food not found.");
        }

        return modelMapper.map(food, FoodDTO.class);
    }

    @Override
    public void delete(Long id) {
        foodRepository.deleteById(id);
    }
}
