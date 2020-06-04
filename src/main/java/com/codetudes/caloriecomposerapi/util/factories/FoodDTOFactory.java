package com.codetudes.caloriecomposerapi.util.factories;

import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.enums.FdcDataType;
import com.codetudes.caloriecomposerapi.util.mappers.BrandedFoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class FoodDTOFactory {

    @Autowired
    BrandedFoodMapper brandedFoodMapper;

    public FoodDTO createFoodDTO(LinkedHashMap<String, Object> fdcResponse, FdcDataType fdcDataType) {
        if (fdcDataType == FdcDataType.BRANDED) {
            return brandedFoodMapper.map(fdcResponse);
        }

        throw new IllegalArgumentException("Invalid FdcDataType");
    }
}
