package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.FoodService;
import com.codetudes.caloriecomposerapi.util.mergers.FoodMerger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FoodMerger foodMerger;

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public FoodDTO create(FoodDTO foodDTO) {
        Food food = modelMapper.map(foodDTO, Food.class);

        // Nutrients logically and physically own the relationship. Set it here.
        food.getNutrients().forEach(nutrient -> nutrient.setFood(food));

        // Portions logically and physically own the relationship. Set it here.
        food.getPortions().forEach(portion -> portion.setFood(food));

        // TODO: Don't hard code this
        List<User> users = userRepository.findAll();
        if (users.size() > 0){
            food.setUser(users.get(0));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Hard-coded user unable to be found while creating food.");
        }

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

        Food mergedFood = foodMerger.merge(foodDTO, existingFood);

        Food updatedFood = foodRepository.save(mergedFood);

        return modelMapper.map(updatedFood, FoodDTO.class);
    }

    @Override
    public void delete(Long id) {
        Food food = foodRepository.findById(id).orElse(null);
        throw404IfNull(food);

        foodRepository.deleteById(food.getId());
    }

    @Override
    public List<FoodDTO> search(String query){
        Pageable pageable = PageRequest.of(0, 5);
        Type targetListType = new TypeToken<List<FoodDTO>>() {}.getType();
        return modelMapper.map(foodRepository.search(query, pageable), targetListType);
    }

    private void throw404IfNull(Food existingFood) {
        if (existingFood == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Food not found.");
        }
    }
}
