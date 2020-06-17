Feature: Tests crud operations for Food type

  Background:
    * def createFoodPayload = read('classpath:payloads/create-food.json')

  # ---------------------------------------------
  # Create
  # ---------------------------------------------

  Scenario: Create a food item
    Given url baseUrl
    And path 'food'
    And request createFoodPayload
    When method post
    Then status 201
    And match response == schemas.food

    # confirm read endpoint works as well
    * call read('classpath:callable/read-food.feature') { id: '#(response.id)'}

    # clean up
    * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

  Scenario Outline: Create a food item and validate '<fieldName>' set appropriately
    Given def payload = createFoodPayload
    And set payload.<fieldName> = <fieldValue>

    Given url baseUrl
    And path 'food'
    And request payload
    When method post
    Then status 201

    # assert field value in create response
    Then match response.<fieldName> == payload.<fieldName>

    # assert field value in separate read response
    Given path 'food', response.id
    When method get
    Then status 200
    And match response.<fieldName> == payload.<fieldName>

    # clean up
    * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

    Examples:
      | fieldName                | fieldValue  |
      | fdcId                    | utils.rs(5) |
      | description              | utils.rs(5) |
      | brandOwner               | utils.rs(5) |
      | ingredients              | utils.rs(5) |
      | servingSize              | utils.rd()  |
      | servingSizeUnit          | utils.rs(5) |
      | householdServingFullText | utils.rs(5) |
      # nested nutrients
      | nutrients[0].name        | utils.rs(5) |
      | nutrients[0].unitName    | utils.rs(5) |
      | nutrients[0].amount      | utils.rd()  |

  # ---------------------------------------------
  # Read
  # ---------------------------------------------

  Scenario: Read a food item
    Given def createFoodResult = call read('classpath:callable/create-food.feature') { request: '#(createFoodPayload)'}

    Given url baseUrl
    And path 'food', createFoodResult.response.id
    When method get
    Then status 200
    And match response == schemas.food

    # clean up
    * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

  # ---------------------------------------------
  # Update
  # ---------------------------------------------

  Scenario Outline: Update a food item and validate '<fieldName>' set appropriately
    Given def createFoodResult = call read('classpath:callable/create-food.feature') { request: '#(createFoodPayload)'}

    Given copy payload = createFoodResult.response
    And set payload.<fieldName> = <fieldValue>

    Given url baseUrl
    And path 'food'
    And request payload
    When method put
    Then status 200

    # assert field value in create response
    Then match response.<fieldName> == payload.<fieldName>

    # assert field value in separate read response
    Given path 'food', response.id
    When method get
    Then status 200
    And match response.<fieldName> == payload.<fieldName>

    # clean up
    * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

    Examples:
      | fieldName                | fieldValue  |
      | fdcId                    | utils.rs(5) |
      | description              | utils.rs(5) |
      | brandOwner               | utils.rs(5) |
      | ingredients              | utils.rs(5) |
      | servingSize              | utils.rd()  |
      | servingSizeUnit          | utils.rs(5) |
      | householdServingFullText | utils.rs(5) |
#      # nested nutrients
      | nutrients[0].name        | utils.rs(5) |
      | nutrients[0].unitName    | utils.rs(5) |
      | nutrients[0].amount      | utils.rd()  |

    @focus
    Scenario: Update a food item by removing a nutrient
      Given def createFoodResult = call read('classpath:callable/create-food.feature') { request: '#(createFoodPayload)'}

      Given copy payload = createFoodResult.response
      And remove payload $.nutrients[1]
      And def remainingNutrientId = payload.nutrients[0].id

      Given url baseUrl
      And path 'food'
      And request payload
      When method put
      Then status 200

      # assert nutrient removed (array is length 1 and remaining nutrient id checks out)
      Then match response.nutrients == '#[1]'
      Then match response.nutrients[0].id == remainingNutrientId

      # assert again against separate read response
      Given path 'food', response.id
      When method get
      Then status 200

      # assert nutrient removed (array is length 1 and remaining nutrient id checks out)
      Then match response.nutrients == '#[1]'
      Then match response.nutrients[0].id == remainingNutrientId

      # clean up
      * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

  # ---------------------------------------------
  # Delete
  # ---------------------------------------------

  Scenario: Delete a food item
    Given def createFoodResult = call read('classpath:callable/create-food.feature') { request: '#(createFoodPayload)'}

    Given url baseUrl
    And path 'food', createFoodResult.response.id
    When method delete
    Then status 200

    # confirm we cannot read item anymore
    Then path 'food', createFoodResult.response.id
    When method get
    Then status 404
