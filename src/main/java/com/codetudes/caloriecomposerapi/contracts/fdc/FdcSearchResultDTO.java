package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

import java.util.List;

@Data
public class FdcSearchResultDTO {
    private Long totalHits;

    private Long totalPages;

    private Long currentPage;

    private List<FdcFoodDTO> foods;
}
