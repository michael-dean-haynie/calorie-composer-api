Feature: Callable feature that creates a comboFood item

  Scenario: Create a comboFood item
    # map input args to args for create food feature
    * def createFoodFeatureArgs = karate.map(__arg.foods, function(food){ return { request: food}; })

    # create constituent foods
    When def result = call read('classpath:callable/crud/food/create-food.feature') createFoodFeatureArgs
    * def constFoods = $result[*].response

    # merge const foods with partial combo food (assign by index)
    * copy comboFoodPL = __arg.partialComboFood
    * karate.forEach(comboFoodPL.foodAmounts, function(fdAm, idx){ fdAm.food = constFoods[idx]; })

    # create comboFood
    Given url baseUrl
    And path 'comboFood'
    And request comboFoodPL
    When method post
    Then status 201
    * def comboFood = response