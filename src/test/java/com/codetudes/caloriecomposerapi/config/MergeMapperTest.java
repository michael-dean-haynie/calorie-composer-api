package com.codetudes.caloriecomposerapi.config;


import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.domain.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MergeMapperTest {
    MergeMapper mergeMapper = new MergeMapper();

    @Test
    void goofing_around() {

        Food food = new Food();
        food.setSsrDisplayUnit(new Unit());

        FoodDTO foodDTO = new FoodDTO();

        mergeMapper.map(foodDTO, food);

//        assertNull(food.getSsrDisplayUnit());

    }
}