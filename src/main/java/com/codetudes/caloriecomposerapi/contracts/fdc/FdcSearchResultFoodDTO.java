package com.codetudes.caloriecomposerapi.contracts.fdc;

import lombok.Data;

@Data
public class FdcSearchResultFoodDTO extends FdcFoodDTO {
    private String brandOwner;

    private String ingredients;

    // Choosing not to include nutrients. They exist but don't have enough data it seems to be useful
}
