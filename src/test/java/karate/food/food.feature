Feature: Tests crud operations for Food type

  Background:
    Given url baseUrl

    * def createFoodPayload = read('classpath:payloads/create-food.json')

  # ---------------------------------------------
  # Create ... (moved)
  # ---------------------------------------------

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
  # Update ... (moved)
  # ---------------------------------------------

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