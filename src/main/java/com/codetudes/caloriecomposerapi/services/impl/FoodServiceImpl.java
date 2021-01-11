package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.FoodRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UnitRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.FoodService;
import com.codetudes.caloriecomposerapi.services.UnitService;
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
    private ModelMapper modelMapper;
    @Autowired
    private FoodMerger foodMerger;

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    UnitService unitService;

    @Override
    public FoodDTO create(FoodDTO foodDTO) {
        Food food = foodMerger.merge(foodDTO, new Food());

        food.setUser(getUser());
        if (food.getDraft() != null) {
            if(food.getDraft().getUser() == null){
                food.getDraft().setUser(food.getUser());
            }
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

        if (mergedFood.getDraft() != null) {
            if(mergedFood.getDraft().getUser() == null){
                mergedFood.getDraft().setUser(mergedFood.getUser());
            }
        }

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
    public List<FoodDTO> readDrafts() {
        Type targetListType = new TypeToken<List<FoodDTO>>() {}.getType();
        return modelMapper.map(foodRepository.findDrafts(), targetListType);
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

    private User getUser(){
        // TODO: Don't hard code this
        List<User> users = userRepository.findAll();
        if (users.size() > 0){
            return users.get(0);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Hard-coded user unable to be found while creating food.");
        }
    }
}
