package com.codetudes.caloriecomposerapi.contracts;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultDTO {
    private Long totalHits;

    private Long totalPages;

    private Long currentPage;

    private List<FoodDTO> foods;
}
