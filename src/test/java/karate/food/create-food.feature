Feature: Tests for creating a food item

  Background:
    Given url baseUrl

    * def createFoodPayload = read('classpath:payloads/create-food.json')
    * def minimalFoodPayload = read('classpath:payloads/create-food-minimal.json')

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
    * copy draft = payload
    * set draft.isDraft = true
    * set payload.draft = draft
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
      | fieldName                              | fieldValue  |
      | fdcId                                  | utils.rs(5) |
      | description                            | utils.rs(5) |
      | brandOwner                             | utils.rs(5) |
      | ingredients                            | utils.rs(5) |
      # display units
      | ssrDisplayUnit.abbreviation            | utils.rs(5) |
      | csrDisplayUnit.abbreviation            | utils.rs(5) |
      # nested nutrients
      | nutrients[0].name                      | utils.rs(5) |
      | nutrients[0].unit.abbreviation         | utils.rs(5) |
      | nutrients[1].amount                    | utils.rd()  |
      # nested conversionRatios
      | conversionRatios[0].amountA            | utils.rd()  |
      | conversionRatios[0].unitA.abbreviation | utils.rs(5) |
      | conversionRatios[0].freeFormValueA     | utils.rs(5) |
      | conversionRatios[0].amountB            | utils.rd()  |
      | conversionRatios[0].unitB.abbreviation | utils.rs(5) |
      | conversionRatios[0].freeFormValueB     | utils.rs(5) |
      | conversionRatios[1].amountA            | utils.rd()  |
      | conversionRatios[1].unitA.abbreviation | utils.rs(5) |
      | conversionRatios[1].freeFormValueA     | utils.rs(5) |
      | conversionRatios[1].amountB            | utils.rd()  |
      | conversionRatios[1].unitB.abbreviation | utils.rs(5) |
      | conversionRatios[1].freeFormValueB     | utils.rs(5) |
      #### nested draft properties
      | draft.fdcId                                  | utils.rs(5) |
      | draft.description                            | utils.rs(5) |
      | draft.brandOwner                             | utils.rs(5) |
      | draft.ingredients                            | utils.rs(5) |
      # display units
      | draft.ssrDisplayUnit.abbreviation            | utils.rs(5) |
      | draft.csrDisplayUnit.abbreviation            | utils.rs(5) |
      # nested nutrients
      | draft.nutrients[0].name                      | utils.rs(5) |
      | draft.nutrients[0].unit.abbreviation         | utils.rs(5) |
      | draft.nutrients[1].amount                    | utils.rd()  |
      # nested conversionRatios
      | draft.conversionRatios[0].amountA            | utils.rd()  |
      | draft.conversionRatios[0].unitA.abbreviation | utils.rs(5) |
      | draft.conversionRatios[0].freeFormValueA     | utils.rs(5) |
      | draft.conversionRatios[0].amountB            | utils.rd()  |
      | draft.conversionRatios[0].unitB.abbreviation | utils.rs(5) |
      | draft.conversionRatios[0].freeFormValueB     | utils.rs(5) |
      | draft.conversionRatios[1].amountA            | utils.rd()  |
      | draft.conversionRatios[1].unitA.abbreviation | utils.rs(5) |
      | draft.conversionRatios[1].freeFormValueA     | utils.rs(5) |
      | draft.conversionRatios[1].amountB            | utils.rd()  |
      | draft.conversionRatios[1].unitB.abbreviation | utils.rs(5) |
      | draft.conversionRatios[1].freeFormValueB     | utils.rs(5) |

  Scenario: Create food item with minimal data
    Given path 'food'
    And request minimalFoodPayload
    When method post
    Then status 201
    And match response == schemas.food

    # confirm read endpoint works as well
    When call read('classpath:callable/crud/food/read-food.feature') { id: '#(response.id)'}

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

  Scenario: Create food item with draft
    * copy minimalFoodWithDraftPayload = minimalFoodPayload
    * set minimalFoodWithDraftPayload.draft = {}

    Given path 'food'
    And request minimalFoodWithDraftPayload
    When method post
    Then status 201
    And match response == schemas.food

    # validate isDraft set properly
    * match response !contains {isDraft: true};
    * match response.draft contains {isDraft: true};

    # confirm read endpoint works as well
    When def readResult = call read('classpath:callable/crud/food/read-food.feature') { id: '#(response.id)'}
    * match readResult.response !contains {isDraft: true};
    * match readResult.response.draft contains {isDraft: true};

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}