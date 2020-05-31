package com.codetudes.caloriecomposerapi.clients.requestModels;

import lombok.Data;

import java.util.List;

@Data
public class FDCFoodsCriteria {
    List<Long> fdcIds;
}
