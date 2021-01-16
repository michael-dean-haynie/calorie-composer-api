package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.clients.FdcClient;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcFoodDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcSearchResultDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.enums.FdcDataType;
import com.codetudes.caloriecomposerapi.services.FdcService;
import com.codetudes.caloriecomposerapi.util.factories.FoodDTOFactory;
import com.codetudes.caloriecomposerapi.util.mappers.SearchResultFoodMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FdcServiceImpl implements FdcService {

    @Autowired
    FdcClient fdcClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FoodDTOFactory foodDTOFactory;

    @Autowired
    SearchResultFoodMapper searchResultFoodMapper;

    public FoodDTO getFoodByFdcId(String fdcId) {
        LinkedHashMap<String, Object> fdcResponse = fdcClient.getFoodByFdcId(fdcId);

        // convert to FdcFoodDTO to determine food data type
        FdcFoodDTO fdcFoodDTO = objectMapper.convertValue(fdcResponse, FdcFoodDTO.class);


        return foodDTOFactory.createFoodDTO(fdcResponse, fdcFoodDTO.getDataType());
    }



    public Page<FoodDTO> search(String query, Pageable pageable) {

        // Get string value for FdcDataType.BRANDED
        String fdcDataType = null;
        try {
            fdcDataType = objectMapper.writeValueAsString(FdcDataType.BRANDED).replace("\"", "");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to map FdcDataType to String");
        }

        // Hit FDC API with search / pagination
        FdcSearchResultDTO searchResponse = fdcClient.search(query, fdcDataType, pageable.getPageSize(), pageable.getPageNumber() + 1);

        // Map to FoodDTO for response
        List<FoodDTO> resultDTOs = searchResponse.getFoods().stream().map(searchResultFood -> {
            return searchResultFoodMapper.map(searchResultFood);
        }).collect(Collectors.toList());

        Page<FoodDTO> pageResult = new PageImpl<>(resultDTOs, pageable, searchResponse.getTotalHits());
        return pageResult;
    }

}
