@focus
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

    # assert updated value in get response
    * match response.<fieldName> == payload.<fieldName>

    # assert other values didn't change
    * match response == almstInitial

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

    Examples:
      | fieldName                          | fieldValue  |
      | fdcId                              | utils.rs(5) |
      | description                        | utils.rs(5) |
      | brandOwner                         | utils.rs(5) |
      | ingredients                        | utils.rs(5) |
      # units themselves not meant to be updated via this endpoint
      # nested nutrients
      | nutrients[0].name                  | utils.rs(5) |
      | nutrients[1].amount                | utils.rd()  |
      # nested conversionRatios
      | conversionRatios[0].amountA        | utils.rd()  |
      | conversionRatios[0].freeFormValueA | utils.rs(5) |
      | conversionRatios[0].amountB        | utils.rd()  |
      | conversionRatios[0].freeFormValueB | utils.rs(5) |
      | conversionRatios[1].amountA        | utils.rd()  |
      | conversionRatios[1].freeFormValueA | utils.rs(5) |
      | conversionRatios[1].amountB        | utils.rd()  |
      | conversionRatios[1].freeFormValueB | utils.rs(5) |


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

  Scenario: Update a food with collection elements in a different order
    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(createFoodPayload)'}

    # swap order of conversionRatios
    * copy responseCopy = createFoodResult.response
    * set createFoodResult.response.conversionRatios[0] = responseCopy.conversionRatios[1]
    * set createFoodResult.response.conversionRatios[1] = responseCopy.conversionRatios[0]

    When call read('classpath:callable/crud/food/update-food.feature') { request: '#(createFoodResult.response)'}
    # TODO: make assertions

  # TODO: Tests adding / removing conversionRatios too.

  Scenario: Update a food item by adding a draft to it
    # Create what will be the "existing food item"
    When def existingResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(createFoodPayload)'}
    * copy updatePayload = existingResult.response

    # Add a draft to the existing food item
    * copy draft =  existingResult.response
    * set draft.isDraft = true
    * remove draft.id
    * karate.map(draft.conversionRatios, utils.removeId)
    * karate.map(draft.nutrients, utils.removeId)
    * set updatePayload.draft = draft

    When def updateResult = call read('classpath:callable/crud/food/update-food.feature') { request: '#(updatePayload)'}

    # Assert draft set up correctly
    * match updateResult.response.draft contains { id: '#notnull' }
    * match updateResult.response.draft.id != updateResult.response.id

    # Assert draft values after separate GET request
    When def getResult = call read('classpath:callable/crud/food/read-food.feature') { id: '#(updateResult.response.id)'}
    * match getResult.response.draft contains { id: '#notnull' }
    * match getResult.response.draft.id != getResult.response.id

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(getResult.response.id)'}

  Scenario: Update a food item by removing its draft
    # Create what will be the "existing food item"
    When def existingResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(createFoodPayload)'}
    * copy updatePayload = existingResult.response

    # Add a draft to the existing food item
    * copy draft =  existingResult.response
    * set draft.isDraft = true
    * remove draft.id
    * karate.map(draft.conversionRatios, utils.removeId)
    * karate.map(draft.nutrients, utils.removeId)
    * set updatePayload.draft = draft
    When def updateResult = call read('classpath:callable/crud/food/update-food.feature') { request: '#(updatePayload)'}

    # Remove draft
    * remove updateResult.response.draft
    When def updateResult2 = call read('classpath:callable/crud/food/update-food.feature') { request: '#(updateResult.response)'}

    # Assert draft removed
    * match updateResult2.response contains { draft: '#null' }

    # Assert draft values after separate GET request
    When def getResult = call read('classpath:callable/crud/food/read-food.feature') { id: '#(updateResult2.response.id)'}
    * match getResult.response contains { draft: '#null' }

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(getResult.response.id)'}

  Scenario Outline: Update a food item's draft and validate '<fieldName>' set appropriately
    # create food with draft
    * copy initialPayload = createFoodPayload
    * copy draft = createFoodPayload
    * set draft.isDraft = true
    * set initialPayload.draft = draft

    When def createFoodResult = call read('classpath:callable/crud/food/create-food.feature') { request: '#(initialPayload)'}

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

    # assert updated value in get response
    * match response.<fieldName> == payload.<fieldName>

    # assert other values didn't change
    * match response == almstInitial

    # clean up
    When call read('classpath:callable/crud/food/delete-food.feature') { id: '#(response.id)'}

    Examples:
      | fieldName                                | fieldValue  |
      | draft.fdcId                              | utils.rs(5) |
      | draft.description                        | utils.rs(5) |
      | draft.brandOwner                         | utils.rs(5) |
      | draft.ingredients                        | utils.rs(5) |
      # units themselves not meant to be updated via this endpoint
      # nested nutrients
      | draft.nutrients[0].name                  | utils.rs(5) |
      | draft.nutrients[1].amount                | utils.rd()  |
      # nested conversionRatios
      | draft.conversionRatios[0].amountA        | utils.rd()  |
      | draft.conversionRatios[0].freeFormValueA | utils.rs(5) |
      | draft.conversionRatios[0].amountB        | utils.rd()  |
      | draft.conversionRatios[0].freeFormValueB | utils.rs(5) |
      | draft.conversionRatios[1].amountA        | utils.rd()  |
      | draft.conversionRatios[1].freeFormValueA | utils.rs(5) |
      | draft.conversionRatios[1].amountB        | utils.rd()  |
      | draft.conversionRatios[1].freeFormValueB | utils.rs(5) |



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

    #TODO confirm orphaned things such as drafts and conversion ratios and nutrients are deleted?