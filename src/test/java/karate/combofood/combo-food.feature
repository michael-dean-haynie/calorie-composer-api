@ignore
Feature: Tests crud operations for ComboFood type

  Background:
    Given url baseUrl

    # prepare payloads
    * def tunaPL = read('classpath:payloads/create-food-tuna.json')
    * def mayoPL = read('classpath:payloads/create-food-mayo.json')
    * def relishPL = read('classpath:payloads/create-food-relish.json')
    * def ryeBreadPL = read('classpath:payloads/create-food-rye-bread.json')

    # prepare partial payloads
    * def tunaSandwichPPL = read('classpath:payloads/partial/create-combo-food-tuna-sandwich.json')

  # ---------------------------------------------
  # Create
  # ---------------------------------------------

  Scenario: Create a comboFood item (Tuna Sandwich)
    # Create foods
    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(tunaPL)'}
    * copy tuna = createFoodResult.response

    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(mayoPL)'}
    * copy mayo = createFoodResult.response

    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(relishPL)'}
    * copy relish = createFoodResult.response

    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(ryeBreadPL)'}
    * copy ryeBread = createFoodResult.response

    # prepare comboFood payload
    * copy tunaSandwichPL = tunaSandwichPPL
    * set tunaSandwichPL.foodAmounts[0].food = tuna
    * set tunaSandwichPL.foodAmounts[1].food = mayo
    * set tunaSandwichPL.foodAmounts[2].food = relish
    * set tunaSandwichPL.foodAmounts[3].food = ryeBread

    # create comboFood
    Given path 'comboFood'
    And request tunaSandwichPL
    When method post
    Then status 201
    And match response == schemas.comboFood

    # confirm read endpoint works as well
    When call read('classpath:callable/crud/combofood/read-combo-food.feature') { id: '#(response.id)'}

    # clean up
    * def constFoodIds = $response.foodAmounts[*].food.id
    When call read('classpath:callable/crud/combofood/delete-combo-food-and-const.feature') { id: '#(response.id)', constFoodIds: '#(constFoodIds)'}

  # ---------------------------------------------
  # Read
  # ---------------------------------------------

  Scenario: Read a comboFood item
    # create comboFood
    When def result = call read('classpath:callable/crud/combofood/create-tuna-sandwich.feature')
    * def comboFood =  result.tunaSandwich

    Given path 'comboFood', comboFood.id
    When method get
    Then status 200
    And match response == schemas.comboFood

    # clean up
    * def constFoodIds = $response.foodAmounts[*].food.id
    When call read('classpath:callable/crud/combofood/delete-combo-food-and-const.feature') { id: '#(response.id)', constFoodIds: '#(constFoodIds)'}

  # ---------------------------------------------
  # Update
  # ---------------------------------------------

  Scenario Outline: Update a comboFood item and validate '<fieldName>' set appropriately
    When def result = call read('classpath:callable/crud/combofood/create-tuna-sandwich.feature')
    * def comboFood =  result.tunaSandwich

    # apply change
    * copy payload = comboFood
    * set payload.<fieldName> = <fieldValue>

    # update
    Given path 'comboFood'
    And request payload
    When method put
    Then status 200

    # assert updated value in create response
    * match response.<fieldName> == payload.<fieldName>

    # assert other values didn't change
    * copy almstInitial = comboFood
    * set almstInitial.<fieldName> = '#ignore'
    * match response == almstInitial

    # assert update value in separate read response
    Given path 'comboFood', response.id
    When method get
    Then status 200

    # assert updated value in create response
    * match response.<fieldName> == payload.<fieldName>

    # assert other values didn't change
    * match response == almstInitial

    # clean up
    * def constFoodIds = $response.foodAmounts[*].food.id
    When call read('classpath:callable/crud/combofood/delete-combo-food-and-const.feature') { id: '#(response.id)', constFoodIds: '#(constFoodIds)'}

    Examples:
      | fieldName                          | fieldValue                                          |
      | description                        | utils.rs(5)                                         |
      # nested food amounts
      | foodAmounts[0].unit                | utils.rs(5)                                         |
      | foodAmounts[0].scalar              | utils.rd()                                          |
#      # nested portions
      | portions[0].metricUnit             | utils.rs(5)                                         |
      | portions[0].metricScalar           | utils.rd()                                          |
      | portions[0].isFoodAmountRefPortion | !tunaSandwichPPL.portions[0].isFoodAmountRefPortion |
      | portions[1].isServingSizePortion   | !tunaSandwichPPL.portions[0].isServingSizePortion   |
      | portions[1].householdUnit          | utils.rs(5)                                         |
      | portions[1].householdScalar        | utils.rd()                                          |


  # ---------------------------------------------
  # Delete
  # ---------------------------------------------

  Scenario: Delete a comboFood item
    When def result = call read('classpath:callable/crud/combofood/create-tuna-sandwich.feature')
    * def comboFood =  result.tunaSandwich

    Given path 'comboFood', comboFood.id
    When method delete
    Then status 200

    # confirm we cannot read comboFood item anymore
    Given path 'comboFood', comboFood.id
    When method get
    Then status 404

    # confirm constituent foods still exist
    * def constFoodIds = $comboFood.foodAmounts[*].food.id
    * def readFoodFeatureArgs = karate.map(constFoodIds, function(val){ return { id: val}; })
    When call read('classpath:callable/crud/food/read-food.feature') readFoodFeatureArgs

    # clean up
    * copy deleteFoodFeatureArgs = readFoodFeatureArgs
    When call read('classpath:callable/crud/food/delete-food.feature') deleteFoodFeatureArgs

