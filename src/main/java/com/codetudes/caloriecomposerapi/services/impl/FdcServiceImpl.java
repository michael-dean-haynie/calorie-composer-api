package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.clients.FdcClient;
import com.codetudes.caloriecomposerapi.contracts.FoodDTO;
import com.codetudes.caloriecomposerapi.contracts.SearchResultDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcFoodDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.FdcSearchResultDTO;
import com.codetudes.caloriecomposerapi.contracts.fdc.enums.FdcDataType;
import com.codetudes.caloriecomposerapi.services.FdcService;
import com.codetudes.caloriecomposerapi.util.factories.FoodDTOFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public FoodDTO getFoodByFdcId(String fdcId) {
        LinkedHashMap<String, Object> fdcResponse = fdcClient.getFoodByFdcId(fdcId);

        // convert to FdcFoodDTO to determine food data type
        FdcFoodDTO fdcFoodDTO = objectMapper.convertValue(fdcResponse, FdcFoodDTO.class);


        return foodDTOFactory.createFoodDTO(fdcResponse, fdcFoodDTO.getDataType());
    }

    public SearchResultDTO search(String query) {

        // Get string value for FdcDataType.BRANDED
        String fdcDataType = null;
        try {
            fdcDataType = objectMapper.writeValueAsString(FdcDataType.BRANDED).replace("\"", "");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to map FdcDataType to String");
        }

        // Hit FDC API for initial search
        FdcSearchResultDTO searchResponse = fdcClient.search(query, fdcDataType, 20L, 1L);

        // Hit FDC API again for more details on search results
        String searchResultIds = String.join(",",
                searchResponse.getFoods().stream().map(FdcFoodDTO::getFdcId).collect(Collectors.toList()));

        List<LinkedHashMap<String, Object>> detailedResults = fdcClient.getFoodsByFdcIds(searchResultIds);

        // Convert FDC DTOs to calorie-composer DTOs
        SearchResultDTO searchResultDTO = new SearchResultDTO();
        searchResultDTO.setTotalHits(searchResponse.getTotalHits());
        searchResultDTO.setTotalPages(searchResponse.getTotalPages());
        searchResultDTO.setCurrentPage(searchResponse.getCurrentPage());
        searchResultDTO.setFoods(detailedResults.stream().map(detailedFood -> {
            // Map via foodDTOFactory
            return foodDTOFactory.createFoodDTO(detailedFood, FdcDataType.BRANDED);
        }).collect(Collectors.toList()));



        return searchResultDTO;
    }

}
