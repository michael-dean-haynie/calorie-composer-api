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

        // map new foodDTO onto existing Food
        modelMapper.map(foodDTO, existingFood);

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
}
