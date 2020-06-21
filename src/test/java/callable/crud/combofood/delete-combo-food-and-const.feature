Feature: Callable feature that deletes a comboFood item and all it's constituent food items

  Scenario: Delete a comboFood item and all it's constituent food items
    # delete comboFood
    Given url baseUrl
    And path 'comboFood', __arg.id
    When method delete
    Then status 200

    # delete constituent food items
    * def deleteFoodFeatureArgs = karate.map(constFoodIds, function(val){ return { id: val}; })
    When call read('classpath:callable/crud/food/delete-food.feature') deleteFoodFeatureArgs
