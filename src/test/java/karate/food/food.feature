Feature: Tests crud operations for Food type

  Background:
    Given url baseUrl

    * def createFoodPayload = read('classpath:payloads/create-food.json')

  # ---------------------------------------------
  # Create
  # ---------------------------------------------

  Scenario: Create a food item
    Given path 'food'
    And request createFoodPayload
    When method post
    Then status 201
    And match response == schemas.food

    # confirm read endpoint works as well
    When call read('classpath:callable/crud/food/read-food.feature') { id: '#(response.id)'}

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

  Scenario Outline: Create a food item and validate '<fieldName>' set appropriately
    * def payload = createFoodPayload
    * set payload.<fieldName> = <fieldValue>

    Given path 'food'
    And request payload
    When method post
    Then status 201

    # assert field value in create response
    * match response.<fieldName> == payload.<fieldName>

    # assert field value in separate read response
    Given path 'food', response.id
    When method get
    Then status 200
    And match response.<fieldName> == payload.<fieldName>

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

    Examples:
      | fieldName                | fieldValue  |
      | fdcId                    | utils.rs(5) |
      | description              | utils.rs(5) |
      | brandOwner               | utils.rs(5) |
      | ingredients              | utils.rs(5) |
      # nested nutrients
      | nutrients[0].name                | utils.rs(5)                                         |
      | nutrients[0].unitName            | utils.rs(5)                                         |
      | nutrients[1].amount              | utils.rd()                                          |
      # nested portions
      | portions[0].baseUnitName         | utils.rs(5)                                         |
      | portions[0].baseUnitAmount       | utils.rd()                                          |
      | portions[0].isNutrientRefPortion | !createFoodPayload.portions[0].isNutrientRefPortion |
      | portions[1].isServingSizePortion | !createFoodPayload.portions[0].isServingSizePortion |
      | portions[1].description          | utils.rs(5)                                         |
      | portions[2].displayUnitName      | utils.rs(5)                                         |
      | portions[2].displayUnitAmount    | utils.rd()                                          |

  # ---------------------------------------------
  # Read
  # ---------------------------------------------

  Scenario: Read a food item
    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(createFoodPayload)'}

    Given path 'food', createFoodResult.response.id
    When method get
    Then status 200
    And match response == schemas.food

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

  # ---------------------------------------------
  # Update
  # ---------------------------------------------

  Scenario Outline: Update a food item and validate '<fieldName>' set appropriately
    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(createFoodPayload)'}

    # stash initial value
    * copy initialFood = createFoodResult.response

    # apply change
    * copy payload = createFoodResult.response
    * set payload.<fieldName> = <fieldValue>

    # update
    Given path 'food'
    And request payload
    When method put
    Then status 200

    # assert updated value in create response
    * match response.<fieldName> == payload.<fieldName>

    # assert other values didn't change
    * copy almstInitial = initialFood
    * set almstInitial.<fieldName> = '#ignore'
    * match response == almstInitial

    # assert update value in separate read response
    Given path 'food', response.id
    When method get
    Then status 200

    # assert updated value in create response
    * match response.<fieldName> == payload.<fieldName>

    # assert other values didn't change
    * match response == almstInitial

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

    Examples:
      | fieldName                        | fieldValue                                          |
      | fdcId                            | utils.rs(5)                                         |
      | description                      | utils.rs(5)                                         |
      | brandOwner                       | utils.rs(5)                                         |
      | ingredients                      | utils.rs(5)                                         |
      # nested nutrients
      | nutrients[0].name                | utils.rs(5)                                         |
      | nutrients[0].unitName            | utils.rs(5)                                         |
      | nutrients[1].amount              | utils.rd()                                          |
      # nested portions
      | portions[0].baseUnitName         | utils.rs(5)                                         |
      | portions[0].baseUnitAmount       | utils.rd()                                          |
      | portions[0].isNutrientRefPortion | !createFoodPayload.portions[0].isNutrientRefPortion |
      | portions[1].isServingSizePortion | !createFoodPayload.portions[0].isServingSizePortion |
      | portions[1].description          | utils.rs(5)                                         |
      | portions[2].displayUnitName      | utils.rs(5)                                         |
      | portions[2].displayUnitAmount    | utils.rd()                                          |


  Scenario: Update a food item by removing a nutrient
    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(createFoodPayload)'}

    # stash nutrient to remove and nutrient to stay
    * def n2r = createFoodResult.response.nutrients[0]
    * assert n2r != null
    * def n2s = createFoodResult.response.nutrients[1]
    * assert n2s != null

    # remove nutrient
    * copy payload = createFoodResult.response
    * remove payload $.nutrients[0]

    Given path 'food'
    And request payload
    When method put
    Then status 200

    # assert only 1 nutrient exists
    * match response.nutrients == '#[1]'
    # assert remaining nutrient is the correct one
    * match response.nutrients[0] == n2s
    # assert removed nutrient is gone
    * assert karate.jsonPath(response, "$.nutrients[?(@.id=='"+n2r.id+"')]")[0] == null

    # assert again against separate read response
    Given path 'food', response.id
    When method get
    Then status 200

    # assert only 1 nutrient exists
    * match response.nutrients == '#[1]'
    # assert remaining nutrient is the correct one
    * match response.nutrients[0] == n2s
    # assert removed nutrient is gone
    * assert karate.jsonPath(response, "$.nutrients[?(@.id=='"+n2r.id+"')]")[0] == null

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

  Scenario: Update a food item by adding a nutrient
    * copy createPayload = createFoodPayload
    * remove createPayload $.nutrients[1]
    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(createPayload)'}

    # assert only 1 nutrient exists
    * match createFoodResult.response.nutrients == '#[1]'

    * copy updatePayload = createFoodResult.response
    * set updatePayload.nutrients[1] = createFoodPayload.nutrients[1]

    Given path 'food'
    And request updatePayload
    When method put
    Then status 200

    # assert nutrient added (array is length 2)
    Then match response.nutrients == '#[2]'

    # assert again against separate read response
    Given path 'food', response.id
    When method get
    Then status 200

    # assert nutrient added (array is length 2)
    Then match response.nutrients == '#[2]'

    # clean up
    * call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

  # TODO: Tests adding / removing portions too.

  # ---------------------------------------------
  # Delete
  # ---------------------------------------------

  Scenario: Delete a food item
    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(createFoodPayload)'}

    Given path 'food', createFoodResult.response.id
    When method delete
    Then status 200

    # confirm we cannot read item anymore
    Given path 'food', createFoodResult.response.id
    When method get
    Then status 404
